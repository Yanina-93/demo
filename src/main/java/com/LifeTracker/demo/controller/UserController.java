package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appusers")
@Tag(name = "AppUsers", description = "Operations related to application users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    //READ ALL
    @Operation(summary = "Get all users", description = "Returns a list of all application users registered in the system.")
    @GetMapping
    public List<AppUser> getAllUsers() {
        return userRepo.findAll();
    }

    //READ ONE
    @GetMapping("/{id}")
    public Optional<AppUser> getUserById(@PathVariable Long id) {
        return userRepo.findById(id);
    }

    //CREATE
    @Operation(summary = "Create a new user", description = "Creates a new application user in the system.")
    @PostMapping
    public AppUser createAppUser(@Valid @RequestBody AppUser appuser) {
        return userRepo.save(appuser);
    }

    //UPDATE
    @Operation(summary = "Update an existing user", description = "Updates an existing application user in the system.")
    @PutMapping("/{id}")
    public AppUser updateUser(@Valid @PathVariable Long id, @RequestBody AppUser updatedUser) {
        return userRepo.findById(id).map(appuser -> {
            appuser.setName(updatedUser.getName());
            appuser.setEmail(updatedUser.getEmail());
            appuser.setPasswordHash(updatedUser.getPasswordHash());
            return userRepo.save(appuser);
        }).orElse(null);
    }
    //DELETE
    @Operation(summary = "Delete an existing user", description = "Deletes an existing application user from the system.")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

}