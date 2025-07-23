package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Event;
import com.LifeTracker.demo.repository.EventRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepo;
    //READ ONE
    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Long id) {
        return eventRepo.findById(id);
    }

    //READ ALL
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }
    //CREATE
    @PostMapping
    public Event createEvent(@Valid@RequestBody Event event) {
        return eventRepo.save(event);
    }

    //UPDATE
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
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventRepo.deleteById(id);
    }    
}