package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.dto.LoginRequest;
import com.LifeTracker.demo.dto.RegisterRequest;
import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // HOME
    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        return "home";
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        model.addAttribute("username", user != null ? user.getUsername() : "Guest");
        return "dashboard";
    }

    // CALENDAR (VISTA)
    @GetMapping("/calendar/page")
    public String calendar() {
        return "calendar";
    }

    // LOGIN (GET - formulario)
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    // LOGIN (POST - proceso)
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("loginRequest") LoginRequest request, Model model) {
        var userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPasswordHash())) {
            model.addAttribute("error", "Invalid credentials.");
            return "login";
        }
        // Opcional: agregar info del usuario a la sesión o modelo
        model.addAttribute("user", userOpt.get());
        model.addAttribute("username", userOpt.get().getEmail());
        // Redirige a dashboard tras login exitoso
        return "redirect:/dashboard";
    }

    // REGISTER (GET - formulario)
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    // REGISTER (POST - proceso)
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerRequest") RegisterRequest request, Model model) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already registered.");
            return "register";
        }
        String role = (request.getRole() != null && !request.getRole().isEmpty()) ? request.getRole() : "USER";
        userService.register(request.getEmail(), request.getPassword(), role, request.getName());
        model.addAttribute("success", "Registration successful! Please login.");
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }
}
