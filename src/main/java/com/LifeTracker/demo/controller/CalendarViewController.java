package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarViewController {

    @Autowired
    private CalendarEventRepository calendarEventRepo;

    // Mostrar calendario con eventos y formulario para agregar
    @GetMapping
    public String showCalendar(Model model) {
        List<CalendarEvent> events = calendarEventRepo.findAll();
        model.addAttribute("events", events);
        model.addAttribute("calendarEvent", new CalendarEvent());
        return "calendar"; // Asegúrate de tener /templates/calendar.html
    }

    // Procesar formulario de nuevo evento
    @PostMapping("/add")
    public String addEvent(@Valid @ModelAttribute("calendarEvent") CalendarEvent event,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            List<CalendarEvent> events = calendarEventRepo.findAll();
            model.addAttribute("events", events);
            return "calendar";
        }
        calendarEventRepo.save(event);
        return "redirect:/calendar";
    }

    // Listar eventos en vista aparte si lo necesitas
    @GetMapping("/list")
    public String listEvents(Model model) {
        List<CalendarEvent> events = calendarEventRepo.findAll();
        model.addAttribute("events", events);
        return "calendar_list"; // (opcional) /templates/calendar_list.html
    }
}

