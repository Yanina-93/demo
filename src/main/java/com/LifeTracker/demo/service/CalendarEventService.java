package com.LifeTracker.demo.service;

import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalendarEventService {
    @Autowired
    private CalendarEventRepository repo;

    public List<CalendarEvent> getEventsForUser(String username) {
        return repo.findByUsername(username);
    }

    public CalendarEvent saveEvent(CalendarEvent event) {
        return repo.save(event);
    }
    // Other methods for updating, deleting, etc. can be added here
}
