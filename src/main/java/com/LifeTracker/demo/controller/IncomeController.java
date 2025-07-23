package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.model.Income;
import com.LifeTracker.demo.repository.IncomeRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepo;

    //READ ALL
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
    @PostMapping
    public Income createIncome(@Valid @RequestBody Income income) {
        return incomeRepo.save(income);
    }
    // UPDATE
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
    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeRepo.deleteById(id);
    }
}
