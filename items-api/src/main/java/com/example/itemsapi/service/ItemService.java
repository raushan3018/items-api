package com.example.itemsapi.service;

import com.example.itemsapi.dto.CreateItemRequest;
import com.example.itemsapi.dto.UpdateItemRequest;
import com.example.itemsapi.model.Item;
import com.example.itemsapi.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for item operations.
 * Handles business logic and coordinates between controller and repository.
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(CreateItemRequest request) {
        String id = UUID.randomUUID().toString();
        Item item = new Item(
                id,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getStockQuantity(),
                Instant.now()
        );
        return itemRepository.save(item);
    }

    public Optional<Item> getItemById(String id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Updates an existing item. Typical ecommerce use case: updating product details
     * from an admin panel.
     */
    public Optional<Item> updateItem(String id, UpdateItemRequest request) {
        return itemRepository.findById(id)
                .map(existing -> {
                    Item updated = new Item(
                            existing.getId(),
                            request.getName(),
                            request.getDescription(),
                            request.getPrice(),
                            request.getCategory(),
                            request.getStockQuantity(),
                            existing.getCreatedAt()
                    );
                    return itemRepository.update(updated).orElse(updated);
                });
    }

    /**
     * Deletes an item by id.
     */
    public boolean deleteItem(String id) {
        return itemRepository.deleteById(id);
    }

    /**
     * Searches items by optional category and price range.
     */
    public List<Item> searchItems(String category, Double minPrice, Double maxPrice) {
        return itemRepository.search(category, minPrice, maxPrice);
    }
}
