package com.store.application.controller;

import com.store.application.dto.BrandDTO;
import com.store.application.dto.BulkBrandDTO;
import com.store.application.model.Brand;
import com.store.application.model.Category;
import com.store.application.repository.BrandRepository;
import com.store.application.repository.CategoryRepository;
import com.store.application.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class BrandController {

    @Autowired
    private BrandService brandservice;
    @Autowired
    private  CategoryRepository categoryRepo;
    @Autowired
    private  BrandRepository brandRepo;


    @GetMapping("/bulk-add")
    public String showBulkForm(Model model) {

        BulkBrandDTO bulk = new BulkBrandDTO();
        bulk.getBrands().add(new BrandDTO()); // only 1 initial row

        model.addAttribute("bulkBrand", bulk);
        model.addAttribute("categories", categoryRepo.findAll());

        return "add-stock";
    }

    @PostMapping("/bulk-save")
    public String saveBulkBrands(@ModelAttribute("bulkBrand") BulkBrandDTO bulkBrand) {
        brandservice.saveBrand(bulkBrand);
        return "redirect:/products/list";
    }

    @GetMapping("/brands/by-category/{id}")
    @ResponseBody
    public List<Brand> getBrandsByCategory(@PathVariable Long id) {
        return brandRepo.findByCategoryId(id);
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("brands", brandservice.getAllProducts());
        return "product-inventory";
    }
}