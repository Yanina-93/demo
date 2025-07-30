package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(AppUser user);
}