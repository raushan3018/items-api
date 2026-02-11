package com.example.itemsapi.repository;

import com.example.itemsapi.model.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * In-memory data store for items using ArrayList.
 * Uses CopyOnWriteArrayList for thread-safe concurrent access.
 * Data is lost when the application restarts.
 *
 * In a real ecommerce application this would be replaced with a database + JPA repository.
 */
@Repository
public class ItemRepository {

    private final List<Item> items = new CopyOnWriteArrayList<>();

    /**
     * Saves a new item to the store.
     */
    public Item save(Item item) {
        items.add(item);
        return item;
    }

    /**
     * Finds an item by its ID.
     */
    public Optional<Item> findById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    /**
     * Returns all items in the store.
     */
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    /**
     * Updates an existing item by replacing it in the in-memory list.
     *
     * @param updated updated item (matched by id)
     * @return Optional with updated item if it existed, otherwise empty
     */
    public Optional<Item> update(Item updated) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updated.getId())) {
                items.set(i, updated);
                return Optional.of(updated);
            }
        }
        return Optional.empty();
    }

    /**
     * Deletes an item by id.
     *
     * @return true if an item was removed, false otherwise
     */
    public boolean deleteById(String id) {
        return items.removeIf(item -> item.getId().equals(id));
    }

    /**
     * Simple search capability for ecommerce-style filtering by category and price range.
     */
    public List<Item> search(String category, Double minPrice, Double maxPrice) {
        return items.stream()
                .filter(item -> category == null || (item.getCategory() != null && item.getCategory().equalsIgnoreCase(category)))
                .filter(item -> minPrice == null || (item.getPrice() != null && item.getPrice() >= minPrice))
                .filter(item -> maxPrice == null || (item.getPrice() != null && item.getPrice() <= maxPrice))
                .toList();
    }
}
