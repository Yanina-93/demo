package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.service.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private CalendarEventService eventService;

    // Muestra la vista de calendario
    @GetMapping("/calendar")
    public String showCalendar(Model model, @AuthenticationPrincipal User user) {
        List<CalendarEvent> events = eventService.getEventsForUser(user.getUsername());
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("newEvent", new CalendarEvent());
        return "calendar";
    }

    // Recibe el formulario
    @PostMapping("/calendar/add")
    public String addEvent(@ModelAttribute("newEvent") CalendarEvent event, @AuthenticationPrincipal User user) {
        event.setUsername(user.getUsername());
        eventService.saveEvent(event);
        return "redirect:/calendar";
    }
}
