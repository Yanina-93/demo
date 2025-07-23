package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appusers")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    //read all
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
    @PostMapping
    public AppUser createAppUser(@Valid @RequestBody AppUser appuser) {
        return userRepo.save(appuser);
    }

    //UPDATE
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
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

}