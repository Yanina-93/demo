package com.LifeTracker.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    private final String BASE_URL = "https://open.er-api.com/v6/latest/EUR";

    public Double getEurToClpRate() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(BASE_URL, String.class);

        System.out.println("RESPUESTA API: " + response);

        org.json.JSONObject obj = new org.json.JSONObject(response);

        if (obj.getString("result").equals("success")) {
            // ["CLP"]
            org.json.JSONObject rates = obj.getJSONObject("rates");
            return rates.getDouble("CLP");
        } else {
            throw new RuntimeException("Error: " + response);
        }
    }
}

