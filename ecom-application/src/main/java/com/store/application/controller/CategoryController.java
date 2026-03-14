package com.store.application.controller;

import com.store.application.model.Category;
import com.store.application.repository.BrandRepository;
import com.store.application.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private BrandRepository brandRepo;

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
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (brandRepo.existsByCategory(category)) {
            redirectAttributes.addFlashAttribute("error",
                    "Cannot delete category. Brands exist under this category.");
            return "redirect:/categories/showAllCategory";
        }

        categoryRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("success",
                "Category deleted successfully.");

        return "redirect:/categories/showAllCategory";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @RequestParam String name,
                                 RedirectAttributes redirect) {

        try {

            Category category = categoryRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            category.setName(name);

            categoryRepo.save(category);

            redirect.addFlashAttribute("success", "Category updated successfully");

        } catch (Exception e) {

            redirect.addFlashAttribute("error", "Something went wrong please try again");

        }
        return "redirect:/categories/showAllCategory";
    }
}