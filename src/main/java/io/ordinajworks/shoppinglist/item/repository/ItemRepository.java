package io.ordinajworks.shoppinglist.item.repository;

import io.ordinajworks.shoppinglist.item.model.Item;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {
    void save(Item item);

    List<Item> getAll();

    Optional<Item> findById(UUID id);
}
