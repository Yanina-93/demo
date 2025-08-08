package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.model.MotivationalQuote;
import com.LifeTracker.demo.repository.MotivationalQuoteRepository;
import com.LifeTracker.demo.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/quotes")
public class MotivationalQuoteController {

    @Autowired
    private MotivationalQuoteRepository quoteRepo;
    @Autowired
    private UserRepository userRepo;

    // --- POPUP INTEGRATION ---

    // 1. got random quote for the popup
    // (when the user clicks on the "Get Quote" button)
    @GetMapping("/random")
    public Map<String, String> getRandomQuote() {
        List<MotivationalQuote> allQuotes = quoteRepo.findAll();
        String quoteText;
        if (!allQuotes.isEmpty()) {
            Random random = new Random();
            quoteText = allQuotes.get(random.nextInt(allQuotes.size())).getText();
        } else {
            // Fallback quote if the list is empty
            quoteText = "¡Hoy es un buen día para empezar algo nuevo!";
        }
        return Map.of("text", quoteText);
    }

    // 2. Save a new quote
    // (when the user submits a new quote from the popup)
     @PostMapping
    public MotivationalQuote createQuote(
            @Valid @RequestBody MotivationalQuote quote,
            @AuthenticationPrincipal User userDetails) {

        // Obtain the email from the authenticated user
        String email = userDetails.getUsername();
        AppUser appUser = userRepo.findByEmail(email).orElse(null);

        // Associate the user with the quote (if it exists)
        if (appUser != null) {
            quote.setAppUser(appUser);
        }

        return quoteRepo.save(quote);
    }
       

    // --- Crud alredy on  ---

    @GetMapping
    public List<MotivationalQuote> getAllQuotes() {
        return quoteRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MotivationalQuote> getQuoteById(@PathVariable Long id) {
        return quoteRepo.findById(id);
    }


    @PutMapping("/{id}")
    public MotivationalQuote updateQuote(@PathVariable Long id, @RequestBody MotivationalQuote updatedQuote) {
        return quoteRepo.findById(id).map(quote -> {
            quote.setText(updatedQuote.getText());
            quote.setAppUser(updatedQuote.getAppUser());
            return quoteRepo.save(quote);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteQuote(@PathVariable Long id) {
        quoteRepo.deleteById(id);
    }

    @GetMapping("/my")
    public List<MotivationalQuote> getMyQuotes(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        AppUser appUser = userRepo.findByEmail(user.getUsername()).orElse(null);
        if (appUser == null) return List.of();
        
        return quoteRepo.findByAppUser(appUser);
    }

}

