package edu.poly.assjava5banhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
    @Autowired
    HttpSession session;

    // Đọc giá trị của attribute trong session 
    @SuppressWarnings("unchecked")
    public <T> T get(String name){
        return (T) session.getAttribute(name);
    }

    // Thay đổi hoặc tạo mới attribute trong session
    public void set(String name, Object value){
        session.setAttribute(name, value);
    }

    // Xóa attribute trong session
    public void remove(String name){
        session.removeAttribute(name);
    }
}   

