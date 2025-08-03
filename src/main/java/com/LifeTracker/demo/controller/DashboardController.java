package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.model.Income;
import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.repository.IncomeRepository;
import com.LifeTracker.demo.repository.ExpenseRepository;
import com.LifeTracker.demo.repository.CalendarEventRepository;
import com.LifeTracker.demo.repository.UserRepository;
import com.LifeTracker.demo.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.List;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private WalletService walletService;

    // DASHBOARD PRINCIPAL
    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal User userDetails) {
        // Get the authenticated user's email
        String email = userDetails.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);

        if (appUser == null) {
            // Si el usuario no existe en la base de datos
            return "redirect:/login";
        }

        // get incomes and expenses for the user
        List<Income> incomes = incomeRepo.findByAppUser(appUser);    
        List<Expense> expenses = expenseRepo.findByAppUser(appUser); 

        // Events per user
        List<CalendarEvent> events = eventRepo.findByUsername(email);

        // Total 
        double totalIncome = incomes.stream().mapToDouble(Income::getAmount).sum();
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();

        // Next 3 upcoming events
        List<CalendarEvent> upcomingEvents = events.stream()
                .sorted((e1, e2) -> e1.getStart().compareTo(e2.getStart()))
                .limit(3)
                .toList();

        // WALLET
        BigDecimal saldo = walletService.getCurrentBalance(email);

        // Time spent in each category
        Map<String, Duration> categoryDurations = events.stream()
                .collect(Collectors.groupingBy(CalendarEvent::getType,
                        Collectors.summingLong(e -> Duration.between(e.getStart(), e.getFinish()).toMillis())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> Duration.ofMillis(e.getValue())));

        // Add attributes to the model
        model.addAttribute("name", appUser.getName());
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("upcomingEvents", upcomingEvents);
        model.addAttribute("incomes", incomes);
        model.addAttribute("expenses", expenses);
        model.addAttribute("events", events);
        model.addAttribute("username", email);
        model.addAttribute("calendarEvent", new CalendarEvent());
        model.addAttribute("saldo", saldo);
        model.addAttribute("categoryDurations", categoryDurations);

        
        return "dashboard";
    }

    // Download summary as Excel
    @GetMapping("/dashboard/download-summary")
    public void downloadSummary(@AuthenticationPrincipal User user, HttpServletResponse response) throws IOException {
        String email = user.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);
        if (appUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
            return;
        }
        List<Expense> expenses = expenseRepo.findByAppUser(appUser);
        List<Income> incomes = incomeRepo.findByAppUser(appUser);

        Workbook workbook = new XSSFWorkbook();

        // Hoja de Gastos
        Sheet expenseSheet = workbook.createSheet("Expenses");
        Row header1 = expenseSheet.createRow(0);
        header1.createCell(0).setCellValue("Date");
        header1.createCell(1).setCellValue("Category");
        header1.createCell(2).setCellValue("Description");
        header1.createCell(3).setCellValue("Amount");
        int rowIdx = 1;
        for (Expense expense : expenses) {
            Row row = expenseSheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(expense.getDate().toString());
            row.createCell(1).setCellValue(expense.getCategory());
            row.createCell(2).setCellValue(expense.getDescription());
            row.createCell(3).setCellValue(expense.getAmount().doubleValue());
        }

        // Hoja de Ingresos
        Sheet incomeSheet = workbook.createSheet("Incomes");
        Row header2 = incomeSheet.createRow(0);
        header2.createCell(0).setCellValue("Date");
        header2.createCell(1).setCellValue("Description");
        header2.createCell(2).setCellValue("Amount");
        rowIdx = 1;
        for (Income income : incomes) {
            Row row = incomeSheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(income.getDate().toString());
            row.createCell(1).setCellValue(income.getDescription());
            row.createCell(2).setCellValue(income.getAmount().doubleValue());
        }

        // Configura la respuesta HTTP para descargar el archivo
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=wallet-summary.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
