package edu.poly.assjava5banhang.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.AccountDAO;
import edu.poly.assjava5banhang.model.Account;



@Controller
@RequestMapping("admin/account")
public class AccountController {

    @Autowired
    AccountDAO accountDAO;

    @GetMapping("index")
    public String index(Model model,
     @ModelAttribute("account") Account account
    ,@RequestParam("p") Optional<Integer> p
    ){
    
        
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Account> page = accountDAO.findAll(pageable);
        model.addAttribute("page", page);
        

        model.addAttribute("views","admin/account/index");

        return "admin/layout";
    }

    @PostMapping("/add")
    public String addProduct( @RequestParam("photoFile") MultipartFile photoFile,
    RedirectAttributes model, @ModelAttribute("account") Account account){

   
        if (!photoFile.isEmpty()) {
            String uploadDir = "D:\\workspace_Java5\\assjava5banhang\\src\\main\\resources\\static\\img\\";
            String fileName = System.currentTimeMillis() + "-" + photoFile.getOriginalFilename();
            File savedFile = new File(uploadDir + fileName);
            try {
                photoFile.transferTo(savedFile);
                account.setPhoto(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/account/index";
            }
            
        }

        accountDAO.save(account);
        model.addAttribute("message", "Create product successfully!");
        return "redirect:/admin/account/index";

    }

    @RequestMapping("/edit/{username}")
    public String edit(Model model,
    @PathVariable("username") String id,@RequestParam("p") Optional<Integer>p
    ){
        Account account = accountDAO.findById(id).orElse(null);
        model.addAttribute("account", account);


        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Account> accounts = accountDAO.findAll(pageable);
        model.addAttribute("page", accounts);

        model.addAttribute("views", "admin/account/index");

        
        return "admin/layout"; 
    }

    @RequestMapping("/update")
    public String updateAccount( @RequestParam("photoFile") MultipartFile photoFile,
    RedirectAttributes model, @ModelAttribute("account") Account account){
        Account existingAccount = accountDAO.findById(account.getUsername()).orElse(null);
        if (!photoFile.isEmpty()) {
            String uploadDir = "D:\\workspace_Java5\\assjava5banhang\\src\\main\\resources\\static\\img\\";
            String fileName = System.currentTimeMillis() + "-" + photoFile.getOriginalFilename();
            File saved = new File(uploadDir + fileName);
            try{
                photoFile.transferTo(saved);
                account.setPhoto(fileName);
            }catch(Exception e){
                e.printStackTrace();
                return "redirect:/admin/account/index?error=upload_failed";

            }
        }else{
            account.setPhoto(existingAccount.getPhoto());
        }
        accountDAO.save(account);
        model.addAttribute("message", "Update account successfully!");
        return "redirect:/admin/account/edit/" + account.getUsername();
    }

    @RequestMapping("/delete/{username}")
    public String delete(@PathVariable("username") String username,Model model){
        accountDAO.deleteById(username);
        return "redirect:/admin/account/index";
    }

    @RequestMapping("/reset")
    public String reset( RedirectAttributes model){
        model.addFlashAttribute("account",new Account());
        return "redirect:/admin/account/index";

    }

    
}
