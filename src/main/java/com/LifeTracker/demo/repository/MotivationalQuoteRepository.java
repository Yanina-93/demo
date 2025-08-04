package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.*;
import com.LifeTracker.demo.model.MotivationalQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MotivationalQuoteRepository extends JpaRepository<MotivationalQuote, Long> {
    // Custom methods if needed
    MotivationalQuote findById(long id);
    MotivationalQuote findFirstByOrderByIdAsc();
    List<MotivationalQuote> findByAppUser(AppUser AppUser);

}
