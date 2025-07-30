package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Income;
import com.LifeTracker.demo.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/incomes")
public class IncomeViewController {

    @Autowired
    private IncomeRepository incomeRepo;

    // Mostrar formulario y lista de ingresos
    @GetMapping
    public String showIncomes(Model model) {
        model.addAttribute("incomes", incomeRepo.findAll());
        model.addAttribute("income", new Income());
        return "incomes"; // templates/incomes.html
    }

    // Procesar el formulario de ingreso
    @PostMapping("/add")
    public String addIncome(@Valid @ModelAttribute("income") Income income,
                            BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("incomes", incomeRepo.findAll());
            return "incomes";
        }
        // Puedes asociar income al usuario si lo necesitas:
        // income.setAppUser(appUserService.findByEmail(principal.getName()).get());
        incomeRepo.save(income);
        return "redirect:/incomes";
    }
}
