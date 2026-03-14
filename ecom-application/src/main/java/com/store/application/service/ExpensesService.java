package com.store.application.service;


import com.store.application.model.Expense;
import com.store.application.model.Sale;
import com.store.application.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpensesService {

    @Autowired
    ExpenseRepository expenseRepository;

    public double getTotalExpenses() {

        return getTodayExpensesList().stream().mapToDouble(Expense::getAmount).sum();

    }

    public List<Expense> getTodayExpensesList() {

        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

       return expenseRepository.findByExpenseDateBetween(startOfDay, endOfDay);
    }

    public List<Expense> getExpensesByDate(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23,59,59);

        return expenseRepository.findByExpenseDateBetween(start,end);
    }
}
