package com.example.bajaj.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class to represent the webhook response from Bajaj API
 * Contains the webhook URL and access token needed for solution submission
 */
public class WebhookResponse {

    @JsonProperty("webhook")
    private String webhook;

    @JsonProperty("accessToken")
    private String accessToken;

    public WebhookResponse() {
    }

    public WebhookResponse(String webhook, String accessToken) {
        this.webhook = webhook;
        this.accessToken = accessToken;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "WebhookResponse{" +
                "webhook='" + webhook + '\'' +
                ", accessToken='" + (accessToken != null ? "***" : "null") + '\'' +
                '}';
    }
}
