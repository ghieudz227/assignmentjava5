package edu.poly.assjava5banhang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.poly.assjava5banhang.dao.CategoryDAO;
import edu.poly.assjava5banhang.model.Category;
import edu.poly.assjava5banhang.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    CategoryDAO dao;
    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    HttpSession session;

    @ModelAttribute
    public void addAttributes(Model model) {
       List<Category> categories = dao.findAll();
        model.addAttribute("categories", categories);
    }

     @ModelAttribute("countCart")
    public int countCart(){
        String username = (String) session.getAttribute("username");
        return shoppingCartService.getCount(username);
    }

}
