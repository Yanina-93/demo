package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Income;
import com.LifeTracker.demo.repository.IncomeRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
@Tag(name = "Incomes", description = "Operations related to incomes")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepo;

    //READ ALL
    @Operation(summary = "Get all incomes", description = "Returns a list of all incomes registered in the system.")
    @GetMapping
    public List<Income> getALLIncomes() {
        return incomeRepo.findAll();
    }
    //READ ONE
    @GetMapping("/{id}")
    public Optional<Income> getIncomeById(@PathVariable Long id) {
        return incomeRepo.findById(id);
    }
    //CREATE
    @Operation(summary = "Create a new income", description = "Creates a new income in the system.")
    @PostMapping
    public Income createIncome(@Valid @RequestBody Income income) {
        return incomeRepo.save(income);
    }
    // UPDATE
    @Operation(summary = "Update an existing income", description = "Updates an existing income in the system.")
    @PutMapping("/{id}")
    public Income updateIncome(@Valid @PathVariable Long id, @RequestBody Income updatedIncome) {
        return incomeRepo.findById(id).map(income -> {
            income.setAmount(updatedIncome.getAmount());
            income.setDescription(updatedIncome.getDescription());
            income.setDate(updatedIncome.getDate());
            income.setAppUser(updatedIncome.getAppUser());
            return incomeRepo.save(income);
        }).orElse(null);
    }

    // DELETE
    @Operation(summary = "Delete an existing income", description = "Deletes an existing income from the system.")
    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeRepo.deleteById(id);
    }
}
