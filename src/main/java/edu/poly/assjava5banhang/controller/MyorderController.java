package edu.poly.assjava5banhang.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.poly.assjava5banhang.dao.AccountDAO;
import edu.poly.assjava5banhang.dao.CategoryDAO;
import edu.poly.assjava5banhang.dao.OrderDAO;
import edu.poly.assjava5banhang.dao.OrderdetailDAO;
import edu.poly.assjava5banhang.model.Account;
import edu.poly.assjava5banhang.model.Category;
import edu.poly.assjava5banhang.model.Order;
import edu.poly.assjava5banhang.model.OrderDetail;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyorderController {

    @Autowired
    OrderDAO dao;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    OrderdetailDAO orderdetailDAO;

    @GetMapping("myOrder")
    public String myOrder(Model model,
    HttpSession session
    ){
        Account user = (Account) session.getAttribute("user");
        if ( user != null) {
            List<Object []> listORder = dao.findOrdersByUsername(user.getUsername());
            System.out.println("list: " +listORder);
            model.addAttribute("myOrder", listORder);
        }

        model.addAttribute("views", "user/myOrder");
        return "user/layout";

    }

    @GetMapping("myOrder/{username}")
    public String myOrder1(Model model,
    @PathVariable("username") String username
    ,
    HttpSession session
    ){

        List<Category> categories = categoryDAO.findAll();

        model.addAttribute("categories1", categories);

        Account account = accountDAO.findByUsername(username);
        
        List<Order> list = dao.findByAccount(account);

        model.addAttribute("myOrder", list);
        

        model.addAttribute("views", "user/myOrdertext");
        return "user/layout";

    }

    @GetMapping("myOrderDetails/{id}")
    public String myOrderDetail(Model model,
    @PathVariable("id") Integer id
    ,
    HttpSession session
    ){

        List<Category> categories = categoryDAO.findAll();

        model.addAttribute("categories1", categories);

        List<OrderDetail> listOd = orderdetailDAO.findHisToryOrderDetails(id);
        model.addAttribute("listOD", listOd);

        int tong = 0;
        for (OrderDetail orderDetail : listOd) {
            tong += (orderDetail.getQuantity() * orderDetail.getPrice());
        }
        
        model.addAttribute("TongTien", tong);
        model.addAttribute("views", "user/myorDerDetails");
        return "user/layout";

    }
}
