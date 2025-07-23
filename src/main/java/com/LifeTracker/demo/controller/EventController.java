package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Event;
import com.LifeTracker.demo.repository.EventRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Operations related to events")
public class EventController {

    @Autowired
    private EventRepository eventRepo;
    //READ ONE
    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Long id) {
        return eventRepo.findById(id);
    }

    //READ ALL
    @Operation(summary = "Get all events", description = "Returns a list of all events registered in the system.")
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }
    //CREATE
    @Operation(summary = "Create a new event", description = "Creates a new event in the system.")
    @PostMapping
    public Event createEvent(@Valid@RequestBody Event event) {
        return eventRepo.save(event);
    }

    //UPDATE
    @Operation(summary = "Update an existing event", description = "Updates an existing event in the system.")
    @PutMapping("/{id}")
    public Event updateEvent(@Valid@PathVariable Long id, @RequestBody Event updatedEvent) {
        return eventRepo.findById(id).map(event -> {
            event.setTitle(updatedEvent.getTitle());
            event.setType(updatedEvent.getType());
            event.setDate(updatedEvent.getDate());
            event.setStartTime(updatedEvent.getStartTime());
            event.setDuration(updatedEvent.getDuration());
            event.setAppUser(updatedEvent.getAppUser());
            return eventRepo.save(event);
        }).orElse(null);
    }

    // DELETE
    @Operation(summary = "Delete an existing event", description = "Deletes an existing event from the system.")
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventRepo.deleteById(id);
    }    
}