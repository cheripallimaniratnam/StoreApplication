package com.store.application.controller;

import com.store.application.dto.ExpenseRequest;
import com.store.application.model.Expense;
import com.store.application.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;


    @GetMapping()
    public String viewExpenses(Model model) {

//        model.addAttribute("expenses", expenseRepo.findAll());

        model.addAttribute("expense", new Expense());

        return "expense";
    }


    @PostMapping("/add")
    public String addExpense(@ModelAttribute ExpenseRequest request,
                             RedirectAttributes redirectAttributes) {

        try {

            if (request.getItems() == null || request.getItems().isEmpty()) {
                throw new RuntimeException("No expenses provided");
            }

            request.getItems().stream()
                    .filter(e -> e.getDescription() != null && e.getAmount() != null)
                    .forEach(expenseRepo::save);

            redirectAttributes.addFlashAttribute(
                    "success", "Expenses saved successfully");

            return "redirect:/expenses";

        } catch (Exception ex) {

            throw new RuntimeException("Error while saving expenses", ex);

        }
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {

        expenseRepo.deleteById(id);

        return "redirect:/expenses";
    }
}