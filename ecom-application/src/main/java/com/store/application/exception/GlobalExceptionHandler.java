package com.store.application.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public String handleInsufficientStock(InsufficientStockException ex,
                                          RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        return "redirect:/sales/new";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public String handleCategoryNotFound(CategoryNotFoundException ex,
                                         RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/products/bulk-add";
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public String handleInvalidQuantity(InvalidQuantityException ex,
                                        RedirectAttributes redirectAttributes,
                                        HttpServletRequest request) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());

        String referer = request.getHeader("Referer");

        return "redirect:" + (referer != null ? referer : "/sales/new");
    }

    @ExceptionHandler(InvalidBulkBrandException.class)
    public String handleInvalidBulk(InvalidBulkBrandException ex,
                                    RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/products/bulk-add";
    }
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:/home";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex,
                                         RedirectAttributes redirectAttributes, HttpServletRequest request) {

        redirectAttributes.addFlashAttribute("error",
                "Something went wrong. Please try again.");

        String referer = request.getHeader("Referer");

        if (referer != null) {
            return "redirect:" + referer;
        }

        return "redirect:/home";
    }
}