package com.LifeTracker.demo.controller;
import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;
import com.LifeTracker.demo.security.JwtUtil;
import com.LifeTracker.demo.dto.*;

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
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    // REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        //verify if email is already registered
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already registered.");
        }
        AppUser newUser = new AppUser();
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setName(request.getName()); //set the name here
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully.");
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials (email not registered).");
        }
        AppUser user = optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials (incorrect password).");
        }
        // Generate the JWT using the email and role
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    
    }
}