package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;
import com.LifeTracker.demo.security.JwtUtil;
import com.LifeTracker.demo.dto.*;
import com.LifeTracker.demo.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;  


import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Email already registered."));
        }
        String role = (request.getRole() != null && !request.getRole().isEmpty()) ? request.getRole() : "USER";
        Optional<AppUser> registerResult = userService.register(
            request.getEmail(),
            request.getPassword(),
            role,
            request.getName()
        );
        if (registerResult.isPresent()) {
            // If the user already exists, we return a bad request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Email already registered."));
        }
        // Successful registration
        return ResponseEntity.ok(
            new MessageResponse("User registered successfully: " + request.getEmail())
        );
    }


    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<AppUser> optionalUser = userService.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid credentials (email not registered)."));
        }
        AppUser user = optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid credentials (incorrect password)."));
        }
        String token = jwtUtil.generateToken(user.getEmail()); 
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getName(), user.getRole()));
    }
}
