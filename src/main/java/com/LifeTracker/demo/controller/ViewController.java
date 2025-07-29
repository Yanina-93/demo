package com.LifeTracker.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
public class ViewController {
    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName(); 
    model.addAttribute("username", username);
    return "home";
    }
    @GetMapping("/calendar/page")
    public String calendar() {
        return "calendar";
    }

     @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        model.addAttribute("username", user != null ? user.getUsername() : "Guest");
        return "dashboard";
    }
}
