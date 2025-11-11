package com.example.bajaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main Spring Boot Application for Bajaj Finserv Health Qualifier
 * 
 * This application:
 * 1. Starts up and automatically sends a POST request to generate a webhook
 * 2. Receives a webhook URL and access token
 * 3. Solves an assigned SQL problem based on registration number
 * 4. Submits the solution to the webhook URL using JWT authentication
 * 
 * No REST endpoints are exposed - the entire flow runs on startup
 */
@SpringBootApplication
public class BajajQualifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(BajajQualifierApplication.class, args);
    }

    /**
     * Bean configuration for RestTemplate
     * Used for making HTTP requests to the Bajaj API
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
