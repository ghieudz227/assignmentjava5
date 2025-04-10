package edu.poly.assjava5banhang.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.AccountDAO;
import edu.poly.assjava5banhang.model.Account;
import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateProfileController {

    @Autowired
    AccountDAO dao;

    @Autowired
    HttpSession session;

    @GetMapping("update-profile")
    public String updateProfile(Model model) {
        Account user = (Account) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userlogin", user);
            
        }
        model.addAttribute("views", "user/update-profile");
        return "user/layout";
    }

    @PostMapping("update-profile")
    public String updateProfilePost(
    @RequestParam("fullname") String fullname,
    @RequestParam("email") String email,
    RedirectAttributes  model

    ) {

        Account userlogin = (Account) session.getAttribute("user");
        userlogin.setFullname(fullname);
        userlogin.setEmail(email);
        dao.save(userlogin);


        session.setAttribute("user", userlogin);

        model.addFlashAttribute("message", "Cập nhật thông tin thành công!");
        return "redirect:/update-profile";
    }

    @GetMapping("changepassword")
    public String changepassword(Model model   
    ){

        Account user = (Account) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userlogin", user);           
        }
        model.addAttribute("views", "user/changepassword");
        return "user/layout";
    }

    @PostMapping("changepassword")
    public String changepasswordPost(
        RedirectAttributes model,
        @RequestParam("password") String password,
        @RequestParam("newpass") String newpass,
        @RequestParam("confirmnewpass") String confirmnewpass,
        HttpSession session) {

    // Lấy thông tin người dùng từ session
    Account user = (Account) session.getAttribute("user");

    if (user == null) {
        model.addFlashAttribute("message", "Bạn chưa đăng nhập!");
        return "redirect:/login";
    }

    // Kiểm tra mật khẩu hiện tại có đúng không
    Optional<Account> accountCheck = dao.findByUsernameAndPassword(user.getUsername(), password);
    if (accountCheck.isEmpty()) {
        model.addFlashAttribute("messageCurrentPass", "Mật khẩu hiện tại không đúng!");
        return "redirect:/changepassword";
    }

    // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp không
    if (!newpass.equals(confirmnewpass)) {
        model.addFlashAttribute("messageConfirmpass", "Mật khẩu mới không khớp với mật khẩu xác nhận!");
        return "redirect:/changepassword";
    }

    // Cập nhật mật khẩu mới
    user.setPassword(newpass);
    dao.save(user);

    // Cập nhật lại session
    session.setAttribute("user", user);

    model.addFlashAttribute("message", "Cập nhật mật khẩu thành công!");
    return "redirect:/changepassword";
}



}
