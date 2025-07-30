package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByAppUser(AppUser user);
    
}
