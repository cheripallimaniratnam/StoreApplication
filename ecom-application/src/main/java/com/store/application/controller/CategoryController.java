package com.store.application.controller;

import com.store.application.model.Category;
import com.store.application.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/showAllCategory")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("category", new Category());
        return "categories";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category) {

        categoryRepo.save(category);

        return "redirect:/categories/showAllCategory";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {

        categoryRepo.deleteById(id);

        return "redirect:/categories";
    }
}