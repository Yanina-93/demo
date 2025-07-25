package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.service.MotivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/motivation")
public class MotivationController {

    @Autowired
    private MotivationService motivationService;

    @GetMapping("/quote")
    public String getMotivationalQuote() {
        return motivationService.getMotivationalQuote();
    }
}