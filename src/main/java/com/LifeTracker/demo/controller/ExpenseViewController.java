package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.repository.ExpenseRepository;
import com.LifeTracker.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/expenses")
public class ExpenseViewController {

    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private UserRepository userRepo;

    // Show all expenses for the authenticated user
    @GetMapping
    public String showExpenses(Model model, @AuthenticationPrincipal User userDetails) {
        // Find the authenticated user by email
        String email = userDetails.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);

        if (appUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("expenses", expenseRepo.findByAppUser(appUser));
        model.addAttribute("expense", new Expense());
        return "expenses"; // templates/expenses.html
    }

    // Process the expense form
    @PostMapping("/add")
    public String addExpense(@Valid @ModelAttribute("expense") Expense expense,
                             BindingResult result,
                             Model model,
                             @AuthenticationPrincipal User userDetails) {

        String email = userDetails.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);
        if (appUser == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("expenses", expenseRepo.findByAppUser(appUser));
            return "expenses";
        }

        // Associate the expense with the authenticated user
        expense.setAppUser(appUser);
        expenseRepo.save(expense);
        return "redirect:/expenses";
    }
}

