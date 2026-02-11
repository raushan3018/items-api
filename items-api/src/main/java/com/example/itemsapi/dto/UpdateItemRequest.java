package com.example.itemsapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for updating an existing item.
 * For simplicity this behaves like a full update (PUT semantics) where all main fields
 * are expected, which is typical for ecommerce admin use cases (updating product catalog).
 */
@Schema(description = "Request body for updating an existing item")
public class UpdateItemRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 200)
    @Schema(description = "Item name", example = "Wireless Headphones Pro", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 1000)
    @Schema(description = "Item description", example = "Updated description for the product", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Item price in currency units", example = "129.99")
    private Double price;

    @Size(max = 100)
    @Schema(description = "Item category", example = "Electronics")
    private String category;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Schema(description = "Available stock quantity", example = "50")
    private Integer stockQuantity;

    public UpdateItemRequest() {
    }

    public UpdateItemRequest(String name, String description, Double price, String category, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}

