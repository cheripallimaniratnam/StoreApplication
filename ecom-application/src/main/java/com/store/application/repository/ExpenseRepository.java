package com.store.application.repository;

import com.store.application.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByExpenseDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.expenseDate BETWEEN :start AND :end")
    Double getTodayExpenses(LocalDateTime start, LocalDateTime end);
}
