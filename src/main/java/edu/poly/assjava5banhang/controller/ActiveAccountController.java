package edu.poly.assjava5banhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.poly.assjava5banhang.dao.AccountDAO;
import edu.poly.assjava5banhang.model.Account;

@Controller
public class ActiveAccountController {

    @Autowired
    AccountDAO dao;

    @GetMapping("/activate")
        public String showActivationPage(@RequestParam("token") String token, Model model) {
            // Gửi token đến view để form gửi lên khi người dùng xác nhận
            model.addAttribute("token", token);
            return "user/active"; // Tên file HTML, ví dụ activation-confirmation.html
        }



   @PostMapping("/activate/confirm")
    public String confirmActivation(@RequestParam("token") String token, RedirectAttributes model) {
    Account account = dao.findByVerificationToken(token);
    if (account != null) {
        account.setActive(true);
        account.setVerificationToken(null); // Xóa token sau khi kích hoạt
        dao.save(account);
        model.addFlashAttribute("message", "Tài khoản đã được kích hoạt thành công. Bạn có thể đăng nhập ngay bây giờ.");
    } else {
        model.addFlashAttribute("message", "Token kích hoạt không hợp lệ hoặc đã được sử dụng.");
    }
    return "redirect:/register"; // Chuyển hướng tới trang đăng nhập
}


}
