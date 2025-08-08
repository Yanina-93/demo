package com.LifeTracker.demo.service;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.repository.UserRepository;
import com.LifeTracker.demo.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalendarEventService {
    @Autowired
    private CalendarEventRepository repo;

    @Autowired
    private UserRepository userRepository;

    public List<CalendarEvent> getEventsForUser(String username) {
        // Get the user by email
        AppUser appUser = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        return repo.findByAppUser(appUser);
    }

    public CalendarEvent saveEvent(CalendarEvent event) {
        return repo.save(event);
    }
    // Other methods for updating, deleting, etc. can be added here
}
