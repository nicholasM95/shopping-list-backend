package io.ordinajworks.shoppinglist.item.service;

import io.ordinajworks.shoppinglist.item.exception.ItemNotFoundException;
import io.ordinajworks.shoppinglist.item.model.Item;
import io.ordinajworks.shoppinglist.item.repository.ItemRepository;
import io.ordinajworks.shoppinglist.item.resource.request.ItemRequestResource;
import io.ordinajworks.shoppinglist.item.resource.response.ItemResponseResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public ItemResponseResource addItem(ItemRequestResource resource) {
        log.info("Create new item {}", resource.name());
        Item item = Item.builder()
                .id(UUID.randomUUID())
                .name(resource.name())
                .amount(resource.amount())
                .build();

        repository.save(item);
        return new ItemResponseResource(item.getId(), 0, item.getName(), item.getAmount());
    }

    public List<ItemResponseResource> getItems() {
        log.info("Get all items");
        AtomicInteger i = new AtomicInteger(1);
        return repository.getAll()
                .stream()
                .map(item -> new ItemResponseResource(item.getId(), i.getAndIncrement(), item.getName(), item.getAmount()))
                .collect(Collectors.toList());
    }

    public ItemResponseResource getItemById(UUID id) {
        Optional<Item> item = repository.findById(id);
        if (item.isPresent()) {
            return new ItemResponseResource(item.get().getId(), 0, item.get().getName(), item.get().getAmount());
        }
        throw new ItemNotFoundException("Item not found");
    }
}
