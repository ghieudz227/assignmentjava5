package edu.poly.assjava5banhang.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.CategoryDAO;
import edu.poly.assjava5banhang.dao.ProductDAO;
import edu.poly.assjava5banhang.model.Category;
import edu.poly.assjava5banhang.model.Product;


@Controller
public class ProductController {
    
    @Autowired
    ProductDAO dao;

    @Autowired
    CategoryDAO categorydao;

    @RequestMapping("admin/product/index")
    public String adminIndex(Model model,@ModelAttribute("product") Product product,
    @RequestParam("p") Optional<Integer>p
    ){

        Pageable pageable = PageRequest.of(p.orElse(0), 5);

        List<Category>category = categorydao.findAll();
        Page<Product> list = dao.findAll(pageable);
        
        model.addAttribute("products", list);
        
        model.addAttribute("views", "admin/product/index2");
        
        model.addAttribute("categories", category);
        return "/admin/layout";

    }

    @RequestMapping("admin/product/add")
    public String addProduct( @RequestParam("photoFile") MultipartFile photoFile,
    RedirectAttributes model,@ModelAttribute("product") Product product
    ){

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            model.addFlashAttribute("nameError","Tên không được để trống!");
        }
       
        if (product.getPrice()== null  ) {
            model.addFlashAttribute("priceError","Giá  không được để trống!");
            
        }else if (product.getPrice() <= 0) {
            model.addFlashAttribute("priceError","Giá phải lớn hơn 0!");
            return "redirect:/admin/product/index";
        }
        if (product.getAvailable() == null || !product.getAvailable()) {
            model.addFlashAttribute("priceAvailable", "Vui lòng chọn trạng thái!");
            return "redirect:/admin/product/index";
        }


        if (!photoFile.isEmpty()) {
            
            String uploadDir = "D:\\workspace_Java5\\assjava5banhang\\src\\main\\resources\\static\\img\\";
            String fileName = System.currentTimeMillis() + "-" + photoFile.getOriginalFilename();
            File savedFile = new File(uploadDir + fileName);
            try {
                photoFile.transferTo(savedFile);
                product.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/product/index?error=upload_failed";
            }
            
        }

        dao.save(product);
        model.addAttribute("message", "Create product successfully!");
        return "redirect:/admin/product/index";

    }

    @RequestMapping("admin/product/update")
    public String updateProduct( @RequestParam("photoFile") MultipartFile photoFile,
    RedirectAttributes model, @ModelAttribute("product") Product product){
        Product existingProduct = dao.findById(product.getId()).orElse(null);
        if (!photoFile.isEmpty()) {
            String uploadDir = "D:\\workspace_Java5\\assjava5banhang\\src\\main\\resources\\static\\img\\";
            String fileName = System.currentTimeMillis() + "-" + photoFile.getOriginalFilename();
            File saved = new File(uploadDir + fileName);
            try{
                photoFile.transferTo(saved);
                product.setImage(fileName);
            }catch(Exception e){
                e.printStackTrace();
                return "redirect:/admin/product/index?error=upload_failed";

            }
        }else{
            product.setImage(existingProduct.getImage());
        }
        dao.save(product);
        model.addAttribute("message", "Update product successfully!");
        return "redirect:/admin/product/edit/" + product.getId();
    }

    @RequestMapping("admin/product/reset")
    public String reset( RedirectAttributes model){
        model.addFlashAttribute("product",new Product());
        return "redirect:/admin/product/index";

    }

    @RequestMapping("admin/product/edit/{id}")
    public String edit(Model model,
    @PathVariable("id") Integer id,@RequestParam("p") Optional<Integer>p
    ){
        Product product = dao.findById(id).orElse(null);
        model.addAttribute("product", product);

        List<Category> categories = categorydao.findAll(); // Load danh mục
        model.addAttribute("categories", categories);

        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Product> products = dao.findAll(pageable);
        model.addAttribute("products", products);

        model.addAttribute("views", "admin/product/index2");

        
        return "admin/layout"; 
    }

    @RequestMapping("admin/product/delete/{id}")
    public String delete(@PathVariable("id") Integer id,Model model){
        dao.deleteById(id);
        return "redirect:/admin/product/index";
    }

    


}
