package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Find events by user ID
    List<Event> findByUserId(Long userId);
}