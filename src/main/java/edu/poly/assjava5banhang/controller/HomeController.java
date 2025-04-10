package edu.poly.assjava5banhang.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.assjava5banhang.dao.CategoryDAO;
import edu.poly.assjava5banhang.dao.ProductDAO;
import edu.poly.assjava5banhang.model.Product;
import edu.poly.assjava5banhang.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    ProductDAO dao;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    HttpSession session;
    @GetMapping("/home")
    public String home(Model model) {
        String username = (String)session.getAttribute("username");

        model.addAttribute("top8news", dao.findTop8NewestProductsSQL()); // sp moi

        // model.addAttribute("top8bestsell", dao.findBestSellingProducts());

        model.addAttribute("productDT", dao.findProDuctByCategoryDienThoai());

        model.addAttribute("productLT", dao.findProDuctByCategoryLaptop());


        model.addAttribute("productBestsell", dao.findTop8BestSellingProducts());

        model.addAttribute("countCart", shoppingCartService.getCount(username));

        


        model.addAttribute("categories", categoryDAO.findAll());
        
        model.addAttribute("views", "user/home/index");

        return "user/layout";
    }

    @RequestMapping("/home/{category}")
    public String homeBycategory(@PathVariable("category") String category, Model model) {

        List<Product> products = dao.findProDuctByCategory(category);
        model.addAttribute("productby", products);

        String categoryName = categoryDAO.findNameById(category);
        model.addAttribute("categoryName", categoryName);

       
        model.addAttribute("views", "user/home/homebycategory");

        return "user/layout";
    }

    @RequestMapping("/home/search")
    public String searchHome(Model model, 
    @RequestParam String keyword,
    HttpSession session
    ){

        List<Product> list = dao.findByName("%" +keyword + "%");

        model.addAttribute("searchbyname",list);

        model.addAttribute("keyword" , keyword);


        model.addAttribute("views", "/user/home/searchhome");

        return "user/layout";
    }


}
