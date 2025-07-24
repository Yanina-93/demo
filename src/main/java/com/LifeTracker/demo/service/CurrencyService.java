package com.LifeTracker.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class CurrencyService {
    // API key and base URL for the exchange rate API
    private final String API_KEY = "7e18e845501d6d3896cc0a50 ";
    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/EUR/CLP";

    public Double getEurToClpRate() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(BASE_URL, String.class);

        JSONObject obj = new JSONObject(response);
        if(obj.getString("result").equals("success")) {
            return obj.getDouble("conversion_rate");
        } else {
            throw new RuntimeException("Error consultando API de divisas");
        }
    }
}