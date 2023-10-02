package io.ordinajworks.shoppinglist.item.repository;

import io.ordinajworks.shoppinglist.item.model.Item;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ItemMapRepository implements ItemRepository {

    private final Map<UUID, Item> itemMap;

    public ItemMapRepository() {
        this.itemMap = new HashMap<>();
        Item item = Item.builder()
                .id(UUID.fromString("c570dada-e134-44ad-84ef-a2e18d9c9f2d"))
                .name("Toilet paper")
                .amount(1)
                .build();
        this.itemMap.put(item.getId(), item);
    }

    @Override
    public void save(Item item) {
        itemMap.put(item.getId(), item);
    }

    @Override
    public List<Item> getAll() {
        return new ArrayList<>(itemMap.values());
    }

    @Override
    public Optional<Item> findById(UUID id) {
        if (itemMap.containsKey(id)) {
            return Optional.of(itemMap.get(id));
        } else {
            return Optional.empty();
        }
    }
}
