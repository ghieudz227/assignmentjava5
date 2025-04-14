package edu.poly.assjava5banhang.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.OrderDAO;
import edu.poly.assjava5banhang.dao.OrderdetailDAO;
import edu.poly.assjava5banhang.dao.ProductDAO;
import edu.poly.assjava5banhang.model.Account;
import edu.poly.assjava5banhang.model.Order;
import edu.poly.assjava5banhang.model.OrderDetail;
import edu.poly.assjava5banhang.model.Product;
import edu.poly.assjava5banhang.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;


@Controller
public class CartController {

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    HttpSession session;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    OrderdetailDAO orderDetailDAO;
    @Autowired
    ShoppingCartService shoppingCartService;

    @RequestMapping("/cart/index")
    public String index(Model model){

        String username = (String)session.getAttribute("username");

        model.addAttribute("cart", shoppingCartService.getItems(username));
        System.out.println("cart: " + shoppingCartService.getItems(username));
        model.addAttribute("amount", shoppingCartService.getAmount(username));

        model.addAttribute("views", "user/home/cart");

        model.addAttribute("tongTienThanhToan", shoppingCartService.getAmount(username));


        return "user/layout";
    }

    @RequestMapping("/cart/add/{id}")
    public String add(
        @PathVariable("id") Integer id,RedirectAttributes model,
        @RequestHeader(value = "Referer",defaultValue = "/home") String referer,
        HttpSession session
    ){
        Account account = (Account) session.getAttribute("user");
        String username = (String) session.getAttribute("username");
        if(account == null){
            model.addFlashAttribute("message","Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng!");
            return "redirect:" + referer;
        }

        shoppingCartService.add(username,id);
        model.addFlashAttribute("message","Thêm sản phẩm vào giỏ hàng thành công!");
        
        return "redirect:" + referer;
    }

    @RequestMapping("/cart/add2/{id}")
    public String add2(
        @PathVariable("id") Integer id,RedirectAttributes model,
        HttpSession session
    ){
        Account account = (Account) session.getAttribute("user");
        String username = (String) session.getAttribute("username");

        if(account == null){
            model.addFlashAttribute("message","Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng!");
            return "redirect:/cart/index" ;
        }
        shoppingCartService.add(username,id);
        model.addFlashAttribute("message","Thêm sản phẩm vào giỏ hàng thành công!");
        
        return "redirect:/cart/index" ;
    }

    

   

    @RequestMapping("/cart/update/{id}")
    public String update(
        @PathVariable("id") Integer id,
        @RequestParam("qty") Integer quantity
    ){
        String username = (String) session.getAttribute("username");
        shoppingCartService.update(username,id, quantity);
        return "redirect:/cart/index";
    }

    

    @RequestMapping("/cart/remove/{id}")
    public String remove(
        @PathVariable("id") Integer id
    ){
         String username = (String) session.getAttribute("username");
        shoppingCartService.remove(username,id);
        return "redirect:/cart/index";
    }

    @RequestMapping("/cart/clear")
    public String clear(){
        String username = (String) session.getAttribute("username");
        shoppingCartService.clear(username);
        return "redirect:/cart/index";
    }

    @RequestMapping("/cart/removeCompletely/{id}")
    public String removeCompletely(
        @PathVariable("id") Integer id
    ){
        String username = (String) session.getAttribute("username");
        shoppingCartService.removeCompletely(username,id);
        return "redirect:/cart/index";
    }

    @RequestMapping("/cart/checkout")
    public String checkout(RedirectAttributes model,@RequestParam("address") String address) {
        Account account = (Account) session.getAttribute("user");
        String username = (String) session.getAttribute("username");
        if (account == null) {
            model.addFlashAttribute("message", "Bạn cần đăng nhập để thanh toán!");
            return "redirect:/cart/index";
        }

        if(address == null || address.isEmpty() || address.replaceAll(",", "").trim().isEmpty()){
            model.addFlashAttribute("messageAddress", "Vui lòng nhập địa chỉ nhận hàng!");
            return "redirect:/cart/index";        
        }


        // Tạo mới Order
        Order order = new Order();
        order.setAccount(account);
        order.setAddress(address); // Sau này có thể thêm form nhập địa chỉ
        order.setCreateDate(new Date());
        orderDAO.save(order);

        // Thêm OrderDetails
        shoppingCartService.getItems(username).forEach(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(order);
            Product product = productDAO.findById(item.getId()).get();
            orderDetail.setProduct(product);
            orderDetail.setPrice(item.getPrice());
            orderDetail.setQuantity(item.getQty());
            orderDetailDAO.save(orderDetail);
        });

        // Xóa giỏ hàng sau khi thanh toán
        shoppingCartService.clear(username);
        model.addFlashAttribute("successMessage", "Đặt hàng thành công!");

        return "redirect:/cart/index";
    }


    


    
    

}
