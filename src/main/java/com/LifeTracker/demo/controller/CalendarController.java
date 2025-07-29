package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.service.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/calendar/events")
public class CalendarController {

    @Autowired
    private CalendarEventService eventService;

    @GetMapping("/calendar")
    public String showCalendar(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        List<CalendarEvent> events = eventService.getEventsForUser(user.getUsername());
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        return "calendar";
    }
    @GetMapping
    public List<CalendarEvent> getUserEvents(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return eventService.getEventsForUser(user.getUsername());
    }
    @PostMapping("/calendar/events")
    public String addEvent(@ModelAttribute CalendarEvent event, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        event.setUsername(user.getUsername());
        eventService.saveEvent(event);
        return "redirect:/calendar";
    }
}
