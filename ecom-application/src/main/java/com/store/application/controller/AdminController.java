package com.store.application.controller;

import com.store.application.dto.AdminDashboardData;
import com.store.application.model.UserLogin;
import com.store.application.service.AdminService;
import com.store.application.service.ExpensesService;
import com.store.application.service.SaleService;
import com.store.application.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsService service;
    @Autowired
    private SaleService saleService;

    @Autowired
    private ExpensesService expensesService;

    @GetMapping("/add-user")
    public String addUserForm(Model model) {
        model.addAttribute("user", new UserLogin());
        return "add-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") UserLogin user,  RedirectAttributes redirect) {
        try {
                if(service.existsByUsername(user.getUsername())){
                    throw new RuntimeException("Username already exists");
                }
                service.createUser(user);
            redirect.addFlashAttribute("success", "User added successfully");
        }catch(Exception e){
            redirect.addFlashAttribute("error", "Something went wrong please try again");
        }
        return "redirect:/admin/add-user";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        model.addAttribute("todaySales", saleService.getTodaySales());
        model.addAttribute("salesCount", saleService.getTodaySalesCount());
        model.addAttribute("todayRevenue", saleService.getTodayRevenue());
        model.addAttribute("todayCustomers", saleService.getTodayCustomers());
        model.addAttribute("todayExpenses", expensesService.getTotalExpenses());
        model.addAttribute("todayExpenseList", expensesService.getTodayExpensesList());

        return "admin-dashboard";
    }
}
