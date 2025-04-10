package edu.poly.assjava5banhang.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.AccountDAO;
import edu.poly.assjava5banhang.model.Account;
import edu.poly.assjava5banhang.service.MailService;

@Controller
public class ForgotpassController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    @Lazy
    MailService mailService;

    @GetMapping("/forgotpass")
    public String forgotpass() {

        return "user/forgotpass";
    }

    @PostMapping("/forgotpass")
    public String forgotpass2(@RequestParam("email") String email, RedirectAttributes model
    
    ) {
        Optional<Account> userOptional = accountDAO.findByEmail(email);
        if (userOptional.isPresent()) {
            Account user = userOptional.get();
            String subject = "Khôi phục mật khẩu ";
            String body = "Mật khẩu của bạn là : " + user.getPassword();
            mailService.sendEmail(email, subject, body);
            model.addFlashAttribute("message", "Mật khẩu đã được gửi về email của bạn");
            return "redirect:/forgotpass";
        }else{
            model.addFlashAttribute("messageErorr","Email không tồn tại trong hệ thống!");
        }

        return "redirect:/forgotpass";
    
    }
        
}
