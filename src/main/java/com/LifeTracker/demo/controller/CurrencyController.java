package com.LifeTracker.demo.controller;

import com.LifeTracker.demo.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/eur-clp")
    public Double getEurToClpRate() {
        // Llama al método que obtiene el valor actualizado
        return currencyService.getEurToClpRate();
    }
}


