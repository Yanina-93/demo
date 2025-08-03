package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.Income;
import com.LifeTracker.demo.repository.IncomeRepository;
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
@RequestMapping("/incomes")
public class IncomeViewController {

    @Autowired
    private IncomeRepository incomeRepo;

    @Autowired
    private UserRepository userRepo;

    // Mostrar formulario y lista de ingresos
    @GetMapping
    public String showIncomes(Model model, @AuthenticationPrincipal User userDetails) {
        String email = userDetails.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);
        if (appUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("incomes", incomeRepo.findByAppUser(appUser));
        model.addAttribute("income", new Income());
        return "incomes"; // templates/incomes.html
    }

    // Procesar el formulario de ingreso
    @PostMapping("/add")
    public String addIncome(@Valid @ModelAttribute("income") Income income,
                            BindingResult result,
                            Model model,
                            @AuthenticationPrincipal User userDetails) {
        String email = userDetails.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);
        if (appUser == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("incomes", incomeRepo.findByAppUser(appUser));
            return "incomes";
        }
        // Asocia el income al usuario autenticado
        income.setAppUser(appUser);
        incomeRepo.save(income);
        return "redirect:/incomes";
    }
}
