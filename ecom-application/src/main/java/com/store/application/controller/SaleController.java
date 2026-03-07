package com.store.application.controller;

import com.store.application.dto.SaleItemDTO;
import com.store.application.dto.SaleRequestDTO;
import com.store.application.repository.BrandRepository;
import com.store.application.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private BrandRepository brandRepo;

    @GetMapping("/new")
    public String showSaleForm(Model model) {
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("saleRequest", new SaleRequestDTO());
        return "sale-form";
    }

    @PostMapping("/process")
    public String processSale(SaleRequestDTO saleRequest,
                              RedirectAttributes redirectAttributes) {

        try {
            saleService.processSale(saleRequest);
            redirectAttributes.addFlashAttribute("success", "Sale completed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sales/new";
    }
}