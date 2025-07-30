package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.Income;
import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.repository.IncomeRepository;
import com.LifeTracker.demo.repository.ExpenseRepository;
import com.LifeTracker.demo.repository.CalendarEventRepository;
import com.LifeTracker.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private IncomeRepository incomeRepo;
    @Autowired
    private ExpenseRepository expenseRepo;
    @Autowired
    private CalendarEventRepository eventRepo;
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal User userDetails) {
        // Email del usuario autenticado
        String email = userDetails.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);

        if (appUser == null) {
            // Si el usuario no existe en la base de datos
            return "redirect:/login";
        }

        // Recupera ingresos y gastos usando el usuario (requiere relación @ManyToOne AppUser)
        List<Income> incomes = incomeRepo.findByAppUser(appUser);    // Usa findByAppUser
        List<Expense> expenses = expenseRepo.findByAppUser(appUser); // Usa findByAppUser

        // Recupera eventos usando el campo username (String)
        List<CalendarEvent> events = eventRepo.findByUsername(email);

        // Calcular totales
        double totalIncome = incomes.stream().mapToDouble(Income::getAmount).sum();
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();

        // Próximos 3 eventos
        List<CalendarEvent> upcomingEvents = events.stream()
                .sorted((e1, e2) -> e1.getStart().compareTo(e2.getStart()))
                .limit(3)
                .toList();

        // Pasar datos a la vista
        model.addAttribute("name", appUser.getName());
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("upcomingEvents", upcomingEvents);

        return "dashboard";
    }
}
