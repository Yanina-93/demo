package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.repository.ExpenseRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expenses", description = "Operations related to expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;
    //READ ONE
    @GetMapping("/{id}")
    public Optional<Expense> getExpenseById(@PathVariable Long id) {
        return expenseRepo.findById(id);
    }
    //READ ALL
    @Operation(summary = "Get all expenses", description = "Returns a list of all expenses registered in the system.")
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    //Create
    @Operation(summary = "Create a new expense", description = "Creates a new expense in the system.")
    @PostMapping
    public Expense crearGasto(@Valid@RequestBody Expense expense) {
        return expenseRepo.save(expense);
    }

    //UPDATE
    @Operation(summary = "Update an existing expense", description = "Updates an existing expense in the system.")
    @PutMapping("/{id}")
    public Expense updateExpense(@Valid @PathVariable Long id, @RequestBody Expense updatedExpense) {
        return expenseRepo.findById(id).map(expense -> {
            expense.setAmount(updatedExpense.getAmount());
            expense.setDescription(updatedExpense.getDescription());
            expense.setDate(updatedExpense.getDate());
            expense.setAppUser(updatedExpense.getAppUser());
            return expenseRepo.save(expense);
        }).orElse(null);
    }

    //DELETE
    @Operation(summary = "Delete an existing expense", description = "Deletes an existing expense from the system.")
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseRepo.deleteById(id);
    }
}