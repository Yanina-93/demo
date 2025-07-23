package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.repository.ExpenseRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;
    //READ ONE
    @GetMapping("/{id}")
    public Optional<Expense> getExpenseById(@PathVariable Long id) {
        return expenseRepo.findById(id);
    }
    //READ ALL
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    //Create
    @PostMapping
    public Expense crearGasto(@Valid@RequestBody Expense expense) {
        return expenseRepo.save(expense);
    }

    //UPDATE
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
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseRepo.deleteById(id);
    }
}