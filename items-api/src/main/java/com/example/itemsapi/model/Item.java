package com.example.itemsapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * Model representing an item in the collection.
 * Can represent various entities: ecommerce products (like Flipkart), movies (like Netflix), etc.
 */
@Schema(description = "An item in the collection (product, movie, etc.)")
public class Item {

    @Schema(description = "Unique identifier")
    private final String id;

    @Schema(description = "Item name")
    private final String name;

    @Schema(description = "Item description")
    private final String description;

    @Schema(description = "Price (optional)")
    private final Double price;

    @Schema(description = "Category (e.g., Electronics, Movies)")
    private final String category;

    @Schema(description = "Available stock quantity for ecommerce use cases")
    private final Integer stockQuantity;

    @Schema(description = "Creation timestamp")
    private final Instant createdAt;

    public Item(String id,
                String name,
                String description,
                Double price,
                String category,
                Integer stockQuantity,
                Instant createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
