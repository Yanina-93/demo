package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    // methods to find incomes by user ID or other criteria can be added here
}