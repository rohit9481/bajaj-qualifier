package com.example.bajaj.config;

import com.example.bajaj.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Startup runner that executes the webhook flow when the application starts
 * This ensures the entire process runs automatically without requiring any REST endpoints
 */
@Component
public class StartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);
    private final WebhookService webhookService;

    public StartupRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) {
        try {
            logger.info("========================================");
            logger.info("Bajaj Finserv Health Qualifier Started");
            logger.info("========================================");
            webhookService.executeFlow();
        } catch (Exception e) {
            logger.error("Error during startup execution: ", e);
        }
    }
}
