package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.CalendarEvent;
import com.LifeTracker.demo.dto.CalendarEventDTO;
import com.LifeTracker.demo.service.CalendarEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CalendarApiController {

    @Autowired
    private CalendarEventService eventService;

    @GetMapping("/events")
    public List<CalendarEventDTO> getUserEvents(@AuthenticationPrincipal UserDetails user) {
        // Busca eventos del usuario
        List<CalendarEvent> events = eventService.getEventsForUser(user.getUsername());

        // Mapea los eventos a DTO
        return events.stream().map(ev -> new CalendarEventDTO(
                ev.getTitle(),
                ev.getStart().toString(),  // Asegúrate que es formato ISO8601
                ev.getFinish().toString()  // El campo debe llamarse 'end' en el DTO
        )).collect(Collectors.toList());
    }
}
