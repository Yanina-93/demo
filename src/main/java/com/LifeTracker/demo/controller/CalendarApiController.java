package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.service.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class CalendarApiController {

    @Autowired
    private CalendarEventService eventService;

    @GetMapping
    public List<CalendarEvent> getUserEvents(@AuthenticationPrincipal User user) {
        return eventService.getEventsForUser(user.getUsername());
    }
}
