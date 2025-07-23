package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.MotivationalQuote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotivationalQuoteRepository extends JpaRepository<MotivationalQuote, Long> {
    // Custom methods if needed
}
