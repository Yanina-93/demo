package com.LifeTracker.demo.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MotivationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchExternalQuote() {
        String apiUrl = "https://zenquotes.io/api/random";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String body = response.getBody();
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(body);
                if (root.isArray() && root.size() > 0) {
                    JsonNode quoteNode = root.get(0);
                    String quote = quoteNode.get("q").asText();
                    String author = quoteNode.get("a").asText();
                    return quote + " — " + author;
                }
            } catch (Exception e) {
                return "Error parsing quote: " + e.getMessage();
            }
        }
        return "Keep going! (Default quote)";
    }

}
