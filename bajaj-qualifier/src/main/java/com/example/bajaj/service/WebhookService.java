package com.example.bajaj.service;

import com.example.bajaj.model.WebhookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Service to handle webhook communication with Bajaj Finserv Health API
 * Executes the following flow:
 * 1. Generates webhook by sending registration details
 * 2. Receives webhook URL and access token
 * 3. Solves the assigned SQL problem
 * 4. Submits the SQL solution to the webhook URL with JWT authentication
 */
@Service
public class WebhookService {

    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String TEST_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

    private final RestTemplate restTemplate;

    @Value("${app.user.name:Sharan Gupta}")
    private String userName;

    @Value("${app.user.regNo:REG12349}")
    private String regNo;

    @Value("${app.user.email:sharan@example.com}")
    private String userEmail;

    public WebhookService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Main flow: Generate webhook, solve SQL problem, and submit solution
     */
    public void executeFlow() {
        try {
            logger.info("Starting Bajaj Qualifier Webhook Flow...");

            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            if (webhookResponse == null) {
                logger.error("Failed to generate webhook.");
                return;
            }

            logger.info("Webhook generated successfully");
            logger.info("Webhook URL: {}", webhookResponse.getWebhook());
            logger.info("Access Token: {}", webhookResponse.getAccessToken());

            // Step 2: Determine the SQL problem based on regNo
            String finalQuery = solveSQLProblem();

            // Step 3: Submit solution to webhook
            submitSolution(webhookResponse.getWebhook(), webhookResponse.getAccessToken(), finalQuery);

            logger.info("Webhook flow completed successfully");

        } catch (Exception e) {
            logger.error("Error in webhook flow: ", e);
        }
    }

    /**
     * Step 1: Generate webhook by sending user registration details
     */
    private WebhookResponse generateWebhook() {
        try {
            logger.debug("Sending POST request to generate webhook...");

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", userName);
            requestBody.put("regNo", regNo);
            requestBody.put("email", userEmail);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                    GENERATE_WEBHOOK_URL, 
                    request, 
                    WebhookResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                logger.error("Failed to get valid webhook response. Status: {}", response.getStatusCode());
                return null;
            }

        } catch (Exception e) {
            logger.error("Error generating webhook: ", e);
            return null;
        }
    }

    /**
     * Step 2: Solve the SQL problem based on regNo
     * The problem is determined by the last two digits of regNo:
     * - Odd last two digits → Question 1
     * - Even last two digits → Question 2
     */
    private String solveSQLProblem() {
        try {
            // Extract last two digits of regNo
            int lastTwoDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));
            boolean isOdd = lastTwoDigits % 2 != 0;

            logger.info("Last two digits of regNo: {}, Is Odd: {}", lastTwoDigits, isOdd);

            // For demonstration, provide a general SQL query
            // In the actual implementation, you would solve the specific problem
            // based on the question document
            String sqlQuery = generateSQLSolution(isOdd);
            logger.info("Generated SQL Query: {}", sqlQuery);

            return sqlQuery;

        } catch (Exception e) {
            logger.error("Error solving SQL problem: ", e);
            return getDefaultSQLQuery();
        }
    }

    /**
     * Generate SQL solution based on problem type
     */
    private String generateSQLSolution(boolean isOdd) {
        // Example SQL queries - Replace with actual solutions
        if (isOdd) {
            // Question 1 solution (for odd regNo)
            return "SELECT id, name, salary FROM employees WHERE salary > (SELECT AVG(salary) FROM employees) ORDER BY salary DESC;";
        } else {
            // Question 2 solution (for even regNo)
            return "SELECT department, COUNT(*) as emp_count, AVG(salary) as avg_salary FROM employees GROUP BY department HAVING COUNT(*) > 0 ORDER BY emp_count DESC;";
        }
    }

    /**
     * Default SQL query fallback
     */
    private String getDefaultSQLQuery() {
        return "SELECT * FROM employees;";
    }

    /**
     * Step 3: Submit SQL solution to webhook with JWT authentication
     */
    private void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        try {
            logger.debug("Submitting SQL solution to webhook...");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);  // JWT Token in Authorization header

            Map<String, String> body = new HashMap<>();
            body.put("finalQuery", finalQuery);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Solution submitted successfully!");
                logger.info("Response from webhook: {}", response.getBody());
            } else {
                logger.warn("Unexpected response status from webhook: {}", response.getStatusCode());
                logger.info("Response body: {}", response.getBody());
            }

        } catch (Exception e) {
            logger.error("Error submitting solution to webhook: ", e);
        }
    }
}
