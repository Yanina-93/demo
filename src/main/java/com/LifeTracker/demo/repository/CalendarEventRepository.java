package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByUser(AppUser user);
    List<CalendarEvent> findByUsername(String username);
    List<CalendarEvent> findByTitle(String title);
    List<CalendarEvent> findByStartBetween(String start, String end);
    List<CalendarEvent> findByEndBetween(String start, String end);
    List<CalendarEvent> findByUserAndStartBetween(AppUser user, String start, String end);
    List<CalendarEvent> findByUserAndEndBetween(AppUser user, String start, String end);

}

