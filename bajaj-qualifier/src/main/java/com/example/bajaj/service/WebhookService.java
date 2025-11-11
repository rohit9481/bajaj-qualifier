// File: service/WebhookService.java
package com.example.bajaj.service;

import com.example.bajaj.model.WebhookResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void executeFlow() {
        try {
            // Step 1: Send POST request to generate webhook
            String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", "Rohit Yakkundi");
            requestBody.put("regNo", "REG12347");  // <-- Replace with your actual regNo
            requestBody.put("email", "your_email@example.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(generateUrl, request, WebhookResponse.class);
            WebhookResponse webhookResponse = response.getBody();

            if (webhookResponse == null) {
                System.out.println("Failed to get webhook response.");
                return;
            }

            System.out.println("Webhook URL: " + webhookResponse.getWebhook());
            System.out.println("Access Token: " + webhookResponse.getAccessToken());

            // Step 2: Solve SQL problem manually
            String finalQuery = "SELECT * FROM employees WHERE salary > 50000;"; // <-- Replace with your actual SQL

            // Step 3: Send SQL query to webhook using JWT
            sendSQLToWebhook(webhookResponse.getWebhook(), webhookResponse.getAccessToken(), finalQuery);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendSQLToWebhook(String webhookUrl, String accessToken, String finalQuery) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);  // JWT Token

            Map<String, String> body = new HashMap<>();
            body.put("finalQuery", finalQuery);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);
            System.out.println("Response from webhook: " + response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
