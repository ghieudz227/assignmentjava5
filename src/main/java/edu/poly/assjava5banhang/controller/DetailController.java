package edu.poly.assjava5banhang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import edu.poly.assjava5banhang.dao.ProductDAO;
import edu.poly.assjava5banhang.model.Product;

@Controller
public class DetailController {

    @Autowired
    ProductDAO dao;
    
    @RequestMapping("/detail/{id}")
    public String detail(Model model
    ,@PathVariable("id") Integer id
    ){

    Product products = dao.findById(id).orElse(null);
    model.addAttribute("product", products);

    if (products != null) {
        List<Product> cungLoai = dao.findProDuctByCategory(products.getCategory().getId());
        model.addAttribute("cungLoai", cungLoai);
        
    }
    model.addAttribute("views", "user/home/detail");
        return "user/layout";
    }
}
