package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;
import com.LifeTracker.demo.security.JwtUtil;
import com.LifeTracker.demo.dto.*;
import com.LifeTracker.demo.service.UserService;
import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.service.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarEventService eventService;
    @Autowired
    private UserRepository userRepository;

    // Muestra el calendario con la lista de eventos y el formulario para añadir uno nuevo
    @GetMapping
    public String showCalendar(Model model, @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return "redirect:/login";
        }

        String username = user.getUsername();
        List<CalendarEvent> events = eventService.getEventsForUser(username);
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("calendarEvent", new CalendarEvent()); // Para el formulario
        return "calendar";
    }

    // Procesa el formulario de añadir nuevo evento
    @PostMapping("/add")
    public String addEvent(@ModelAttribute("calendarEvent") CalendarEvent event, @AuthenticationPrincipal UserDetails user) {
        AppUser appUser = userRepository.findByEmail(user.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + user.getUsername()));

        event.setAppUser(appUser);
        eventService.saveEvent(event);
        return "redirect:/calendar";
    }

   
}
