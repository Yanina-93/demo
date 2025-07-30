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
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarEventService eventService;

    // Muestra el calendario con la lista de eventos y el formulario para añadir uno nuevo
    @GetMapping
    public String showCalendar(Model model, @AuthenticationPrincipal User user) {
        List<CalendarEvent> events = eventService.getEventsForUser(user.getUsername());
        model.addAttribute("events", events);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("calendarEvent", new CalendarEvent()); // Para el formulario
        return "calendar";
    }

    // Procesa el formulario de añadir nuevo evento
    @PostMapping("/add")
    public String addEvent(@ModelAttribute("calendarEvent") CalendarEvent event, @AuthenticationPrincipal User user) {
        event.setUsername(user.getUsername());
        eventService.saveEvent(event);
        return "redirect:/calendar";
    }

    // Opcional: Endpoint para API de eventos para FullCalendar (si usas AJAX en el frontend)
    @GetMapping("/api/events")
    @ResponseBody
    public List<CalendarEvent> apiEvents(@AuthenticationPrincipal User user) {
        return eventService.getEventsForUser(user.getUsername());
    }
}
