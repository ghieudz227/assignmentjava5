package edu.poly.assjava5banhang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.assjava5banhang.dao.OrderDAO;
import edu.poly.assjava5banhang.dao.OrderdetailDAO;
import edu.poly.assjava5banhang.dto.CategoryStatsDTO;
import edu.poly.assjava5banhang.dto.VipCustomerDTO;

@Controller
@RequestMapping("/reports")
public class ReportController {
    
    @Autowired
    private OrderdetailDAO dao;

    @Autowired
    private OrderDAO oDao;

    @GetMapping("/tongdoanhthu")
    public String getCategory(Model model){

        List<CategoryStatsDTO> stats = dao.getCategoryStats();
        model.addAttribute("thongkeDthu", stats);

        model.addAttribute("views", "admin/report");

        return "admin/layout";

    }

    @GetMapping("/10khachhangvip")
    public String get10Vip(Model model){
        List<VipCustomerDTO> stats = oDao.getVipCustomer();
        model.addAttribute("vipCustomer", stats);

        model.addAttribute("views", "admin/report10vip");

        return "admin/layout";
    }

}
