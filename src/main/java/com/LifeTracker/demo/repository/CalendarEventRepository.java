package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
   List<CalendarEvent> findByAppUser(AppUser appUser);

    
}

