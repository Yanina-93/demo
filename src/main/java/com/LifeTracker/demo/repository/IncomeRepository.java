package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByAppUser(AppUser appUser);
    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE i.appUser.email = :email")
    BigDecimal sumByAppUserEmail(@Param("email") String email);
}