package com.LifeTracker.demo.service;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;
import com.LifeTracker.demo.repository.ExpenseRepository;
import com.LifeTracker.demo.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ExpenseRepository expenseRepo;
    @Autowired
    private IncomeRepository incomeRepo;

    public BigDecimal getCurrentBalance(String email) {

        AppUser user = userRepo.findByEmail(email).orElse(null);
        if (user == null) return BigDecimal.ZERO;
        BigDecimal totalIncome = incomeRepo.sumByAppUserEmail(email);
        BigDecimal totalExpense = expenseRepo.sumByAppUserEmail(email);
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;
        return totalIncome.subtract(totalExpense);
    }
}
