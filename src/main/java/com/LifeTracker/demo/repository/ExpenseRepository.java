package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.Expense;
import com.LifeTracker.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByAppUser(AppUser user);
    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE e.username = :username")
    BigDecimal sumByUsername(@Param("username") String username);
    
}
