package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Methods to find expenses by user ID or other criteria can be added here
}
