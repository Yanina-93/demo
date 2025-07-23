package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Find events by user ID
    //List<Event> findByUserId(Long userId);
}