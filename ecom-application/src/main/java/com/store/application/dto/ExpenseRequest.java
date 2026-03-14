package com.store.application.dto;
import com.store.application.model.Expense;
import java.util.List;

public class ExpenseRequest {

    private List<Expense> items;

    public List<Expense> getItems() {
        return items;
    }

    public void setItems(List<Expense> items) {
        this.items = items;
    }
}