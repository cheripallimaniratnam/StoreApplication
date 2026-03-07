package com.store.application.controller;

import com.store.application.model.Sale;
import com.store.application.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public String reportPage() {
        return "reports";
    }

    @PostMapping("/date")
    public String getSalesByDate(@RequestParam String date, Model model) {

        LocalDate selectedDate = LocalDate.parse(date);

        List<Sale> sales = saleService.getSalesByDate(selectedDate);

        if(sales.isEmpty()){
            model.addAttribute("error","No sales found for selected date");
        }

        double totalRevenue = sales.stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();

        int totalOrders = sales.size();

        int totalQuantity = sales.stream()
                .mapToInt(Sale::getQuantity)
                .sum();

        String topBrand = sales.stream()
                .collect(Collectors.groupingBy(s -> s.getBrand().getName(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        model.addAttribute("sales", sales);
        model.addAttribute("selectedDate", date);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("topBrand", topBrand);

        return "reports";
    }
}