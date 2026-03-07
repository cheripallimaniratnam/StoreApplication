package com.store.application.controller;

import com.store.application.dto.AdminDashboardData;
import com.store.application.model.UserLogin;
import com.store.application.service.AdminService;
import com.store.application.service.SaleService;
import com.store.application.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsService service;
    @Autowired
    private SaleService saleService;

    @GetMapping("/add-user")
    public String addUserForm(Model model) {
        model.addAttribute("user", new UserLogin());
        return "add-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") UserLogin user) {
        service.createUser(user);
        return "redirect:/home";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        model.addAttribute("todaySales", saleService.getTodaySales());
        model.addAttribute("salesCount", saleService.getTodaySalesCount());
        model.addAttribute("todayRevenue", saleService.getTodayRevenue());
        model.addAttribute("todayCustomers", saleService.getTodayCustomers());

        return "admin-dashboard";
    }
}
