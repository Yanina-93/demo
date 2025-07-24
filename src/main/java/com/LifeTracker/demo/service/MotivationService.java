package com.LifeTracker.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class MotivationService {
    private final String ZEN_QUOTES_URL = "https://zenquotes.io/api/random";

    public String getMotivationalQuote() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(ZEN_QUOTES_URL, String.class);

        JSONArray arr = new JSONArray(response);
        JSONObject obj = arr.getJSONObject(0);

        String quote = obj.getString("q");
        String author = obj.getString("a");

        return quote + " — " + author;
    }
}