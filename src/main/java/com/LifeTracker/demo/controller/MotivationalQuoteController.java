package com.LifeTracker.demo.controller;
import com.LifeTracker.demo.model.MotivationalQuote;
import com.LifeTracker.demo.repository.MotivationalQuoteRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quotes")
public class MotivationalQuoteController {

    @Autowired
    private MotivationalQuoteRepository quoteRepo;

    // READ ALL
    @GetMapping
    public List<MotivationalQuote> getAllQuotes() {
        return quoteRepo.findAll();
    }
    // READ ONE
    @GetMapping("/{id}")
    public Optional<MotivationalQuote> getQuoteById(@PathVariable Long id) {
        return quoteRepo.findById(id);
    }
    @PostMapping
    public MotivationalQuote createQuote(@Valid @RequestBody MotivationalQuote quote) {
        return quoteRepo.save(quote);
    }

    // UPDATE
    @PutMapping("/{id}")
    public MotivationalQuote updateQuote(@Valid @PathVariable Long id, @RequestBody MotivationalQuote updatedQuote) {
        return quoteRepo.findById(id).map(quote -> {
            quote.setText(updatedQuote.getText());
            quote.setAppUser(updatedQuote.getAppUser());
            return quoteRepo.save(quote);
        }).orElse(null);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteQuote(@PathVariable Long id) {
        quoteRepo.deleteById(id);
    }

}
