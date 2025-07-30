package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseViewController {

    @Autowired
    private ExpenseRepository expenseRepo;

    // Mostrar formulario y lista de gastos
    @GetMapping
    public String showExpenses(Model model) {
        model.addAttribute("expenses", expenseRepo.findAll());
        model.addAttribute("expense", new Expense());
        return "expenses"; // templates/expenses.html
    }

    // Procesar el formulario de gasto
    @PostMapping("/add")
    public String addExpense(@Valid @ModelAttribute("expense") Expense expense,
                             BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("expenses", expenseRepo.findAll());
            return "expenses";
        }
        // Puedes asociar expense al usuario autenticado si lo necesitas:
        // expense.setAppUser(appUserService.findByEmail(principal.getName()).get());
        expenseRepo.save(expense);
        return "redirect:/expenses";
    }
}

