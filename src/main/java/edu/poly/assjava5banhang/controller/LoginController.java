package edu.poly.assjava5banhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.AccountDAO;
import edu.poly.assjava5banhang.model.Account;
import edu.poly.assjava5banhang.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    AccountDAO dao;

    @Autowired
    HttpSession session;

    @GetMapping("login")
    public String login(@ModelAttribute("user")Account user) {

        return "user/login";
    }

    @PostMapping("login")
    public String loginProcess(RedirectAttributes model
    ,@RequestParam("username") String username,
    @RequestParam("password") String password
    ){
        Account user = dao.findByUsername(username);
        if (user == null) {
            model.addFlashAttribute("usernameError", "Username sai hoặc không tồn tại!");
            return "redirect:/login";
        }
        if (!user.getPassword().equals(password)) {
            model.addFlashAttribute("passwordError", "Sai mật khẩu!");
            return "redirect:/login";
            
        }

        if (!user.isActive()) {
            model.addFlashAttribute("accountError", "Tài khoản của bạn chưa được kích hoạt!");
            return "redirect:/login";
        }
        session.setAttribute("user", user);
        shoppingCartService.setCurrentUser(username);
        
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Hủy toàn bộ session
        return "redirect:/login"; // Chuyển hướng về trang login
    }

  


}