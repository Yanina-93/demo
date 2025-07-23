package com.LifeTracker.demo.controller;
import com.LifeTracker.demo.model.MotivationalQuote;
import com.LifeTracker.demo.repository.MotivationalQuoteRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quotes")
@Tag(name = "Quotes", description = "Operations related to motivational quotes")
public class MotivationalQuoteController {

    @Autowired
    private MotivationalQuoteRepository quoteRepo;

    // READ ALL
    @Operation(summary = "Get all quotes", description = "Returns a list of all motivational quotes registered in the system.")
    @GetMapping
    public List<MotivationalQuote> getAllQuotes() {
        return quoteRepo.findAll();
    }
    // READ ONE
    @GetMapping("/{id}")
    public Optional<MotivationalQuote> getQuoteById(@PathVariable Long id) {
        return quoteRepo.findById(id);
    }
    // CREATE
    @Operation(summary = "Create a new quote", description = "Creates a new motivational quote in the system.")
    @PostMapping
    public MotivationalQuote createQuote(@Valid @RequestBody MotivationalQuote quote) {
        return quoteRepo.save(quote);
    }

    // UPDATE
    @Operation(summary = "Update an existing quote", description = "Updates an existing motivational quote in the system.")
    @PutMapping("/{id}")
    public MotivationalQuote updateQuote(@Valid @PathVariable Long id, @RequestBody MotivationalQuote updatedQuote) {
        return quoteRepo.findById(id).map(quote -> {
            quote.setText(updatedQuote.getText());
            quote.setAppUser(updatedQuote.getAppUser());
            return quoteRepo.save(quote);
        }).orElse(null);
    }

    // DELETE
    @Operation(summary = "Delete an existing quote", description = "Deletes an existing motivational quote from the system.")
    @DeleteMapping("/{id}")
    public void deleteQuote(@PathVariable Long id) {
        quoteRepo.deleteById(id);
    }

}
