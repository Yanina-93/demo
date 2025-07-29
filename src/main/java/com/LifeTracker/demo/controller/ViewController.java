package com.LifeTracker.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;


@Controller
public class ViewController {
    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName(); 
    model.addAttribute("username", username);
    return "home";
    }
    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }
}
