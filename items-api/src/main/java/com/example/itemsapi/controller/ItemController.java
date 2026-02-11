package com.example.itemsapi.controller;

import com.example.itemsapi.dto.CreateItemRequest;
import com.example.itemsapi.dto.UpdateItemRequest;
import com.example.itemsapi.model.Item;
import com.example.itemsapi.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing items.
 * Provides endpoints for adding, retrieving, updating and deleting items.
 * This mimics a small ecommerce-style product catalog API.
 */
@RestController
@RequestMapping("/api/items")
@Tag(name = "Items", description = "API for managing items (e.g., ecommerce products, movies)")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Add a new item to the collection.
     * Validates that name and description are present and satisfy constraints.
     */
    @PostMapping
    @Operation(summary = "Add a new item", description = "Creates a new item with auto-generated ID. Name and description are required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created successfully",
                    content = @Content(schema = @Schema(implementation = Item.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - validation failed")
    })
    public ResponseEntity<Item> addItem(@Valid @RequestBody CreateItemRequest request) {
        Item item = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    /**
     * Get a single item by its ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", description = "Retrieves a single item by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = @Content(schema = @Schema(implementation = Item.class))),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<Item> getItemById(
            @Parameter(description = "Item ID") @PathVariable String id
    ) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all items (bonus endpoint for completeness) or filter using simple ecommerce-style
     * search parameters: category and price range.
     */
    @GetMapping
    @Operation(summary = "Get all items or search", description = "Retrieves all items in the collection, or filters by optional category and price range.")
    public ResponseEntity<?> getAllItems(
            @Parameter(description = "Filter by category") @RequestParam(required = false) String category,
            @Parameter(description = "Filter by minimum price") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Filter by maximum price") @RequestParam(required = false) Double maxPrice
    ) {
        if (category != null || minPrice != null || maxPrice != null) {
            return ResponseEntity.ok(itemService.searchItems(category, minPrice, maxPrice));
        }
        return ResponseEntity.ok(itemService.getAllItems());
    }

    /**
     * Update an existing item (full update).
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing item", description = "Updates an existing item by ID. Typical for ecommerce admin panels.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully",
                    content = @Content(schema = @Schema(implementation = Item.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input - validation failed"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<Item> updateItem(
            @Parameter(description = "Item ID") @PathVariable String id,
            @Valid @RequestBody UpdateItemRequest request
    ) {
        return itemService.updateItem(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete an item by ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an item", description = "Deletes an item by ID. Useful for managing an ecommerce catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<Void> deleteItem(
            @Parameter(description = "Item ID") @PathVariable String id
    ) {
        boolean deleted = itemService.deleteItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
