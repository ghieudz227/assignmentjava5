package edu.poly.assjava5banhang.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.AccountDAO;
import edu.poly.assjava5banhang.model.Account;
import edu.poly.assjava5banhang.service.MailService;



@Controller
public class RegisterController {

    @Autowired
    AccountDAO dao;

    @Autowired
    MailService mailService;

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @RequestMapping("/register")
public String registerProcess(
    RedirectAttributes model,
    @RequestParam("username") String username,
    @RequestParam("password") String password,
    @RequestParam("fullname") String fullname,
    @RequestParam("email") String email
) {
    // Kiểm tra null hoặc rỗng
    if (username == null || username.trim().isEmpty() ||
        password == null || password.trim().isEmpty() ||
        fullname == null || fullname.trim().isEmpty() ||
        email == null || email.trim().isEmpty()) {
        
        model.addFlashAttribute("message", "Vui lòng nhập đầy đủ thông tin.");
        return "redirect:/register";
    }

    // Kiểm tra username hoặc email đã tồn tại
    if (dao.existsByUsername(username)) {
        model.addFlashAttribute("errorUsername", "Tên đăng nhập đã tồn tại.");
        return "redirect:/register";
    }

    if (dao.existsByEmail(email)) {
        model.addFlashAttribute("errorEmail", "Email đã được sử dụng.");
        return "redirect:/register";
    }

    // Tạo tài khoản mới
    Account account = new Account();
    account.setUsername(username);
    account.setPassword(password);
    account.setFullname(fullname);
    account.setEmail(email);
    account.setActive(false);

    // Tạo token kích hoạt
    String token = UUID.randomUUID().toString();
    account.setVerificationToken(token);

    // Lưu vào database
    dao.save(account);

    // Gửi email kích hoạt
    String activationLink = "http://localhost:8080/activate?token=" + token;
    mailService.sendActivationEmail(account.getEmail(), activationLink);

    // Thông báo thành công
    model.addFlashAttribute("message", "Đăng ký thành công! Vui lòng kiểm tra email để xác nhận tài khoản.");
    return "redirect:/register";
}

}
