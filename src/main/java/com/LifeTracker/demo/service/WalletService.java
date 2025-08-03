package com.LifeTracker.demo.service;

import com.LifeTracker.demo.repository.ExpenseRepository;
import com.LifeTracker.demo.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private ExpenseRepository expenseRepo;
    @Autowired
    private IncomeRepository incomeRepo;

    public BigDecimal getCurrentBalance(String username) {
        BigDecimal incomes = incomeRepo.sumByUsername(username);
        BigDecimal expenses = expenseRepo.sumByUsername(username);
        return incomes.subtract(expenses);
    }
}
