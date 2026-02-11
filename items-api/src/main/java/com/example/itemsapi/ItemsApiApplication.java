package com.example.itemsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry point for the Items REST API.
 * Provides endpoints to manage a collection of items (e.g., ecommerce products, movies).
 */
@SpringBootApplication
public class ItemsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemsApiApplication.class, args);
    }
}
