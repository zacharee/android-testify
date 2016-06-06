package com.shopify.testify.plugin;

public class DemoPluginExtension {
    private String message = "Default Greeting from Gradle";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}