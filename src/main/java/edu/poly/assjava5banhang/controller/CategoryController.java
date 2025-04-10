package edu.poly.assjava5banhang.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.CategoryDAO;
import edu.poly.assjava5banhang.model.Category;


@Controller
public class CategoryController {

    @Autowired
    CategoryDAO dao;

    @RequestMapping("admin/category/index")
    public String index(Model model,
    @ModelAttribute("category") Category category,
    @RequestParam("p") Optional<Integer> p
    ){

        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Category> page = dao.findAll(pageable);

        model.addAttribute("page", page);

        model.addAttribute("views", "admin/category/index2");
        return "admin/layout";
    }

    @RequestMapping("admin/category/add")
    public String addProduct( RedirectAttributes model, @ModelAttribute("category") Category category){

        dao.save(category);
        model.addAttribute("message", "Create category successfully!");
        return "redirect:/admin/category/index";

    }

    @RequestMapping("admin/category/edit/{id}")
    public String edit(Model model,
    @PathVariable("id") String id,@RequestParam("p") Optional<Integer>p
    ){
        Category category = dao.findById(id).get();
        model.addAttribute("category", category);

        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Category> categorys = dao.findAll(pageable);
        model.addAttribute("page", categorys);

        model.addAttribute("views", "admin/category/index2");

        
        return "admin/layout";     
    }

    @RequestMapping("admin/category/update")
    public String updateProduct( RedirectAttributes model, @ModelAttribute("category") Category category){

        dao.save(category);
        model.addAttribute("message", "Create category successfully!");
        return "redirect:/admin/category/edit/" + category.getId();

    }

    @RequestMapping("admin/category/reset")
    public String reset( RedirectAttributes model){

         model.addFlashAttribute("product",new Category());
         return "redirect:/admin/category/index";

    }

    @RequestMapping("admin/category/delete/{id}")
    public String delete(@PathVariable("id") String id,Model model){
        dao.deleteById(id);
        
        return "redirect:/admin/category/index";
    }
}
