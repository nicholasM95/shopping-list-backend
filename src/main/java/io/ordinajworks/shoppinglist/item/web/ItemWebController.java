package io.ordinajworks.shoppinglist.item.web;

import io.ordinajworks.shoppinglist.item.resource.request.ItemRequestResource;
import io.ordinajworks.shoppinglist.item.resource.response.ItemResponseResource;
import io.ordinajworks.shoppinglist.item.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@CrossOrigin({"http://localhost:4200", "https://shopping-list.tst.ordina-jworks.io"})
@RestController
@RequestMapping("/item")
public class ItemWebController {

    private final ItemService service;

    public ItemWebController(ItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ItemResponseResource> addItem(@RequestBody ItemRequestResource resource) {
        ItemResponseResource response = service.addItem(resource);
        UriComponents uriComponents = UriComponentsBuilder.newInstance().path("/item/{}").buildAndExpand(response.id());
        return ResponseEntity.created(uriComponents.toUri()).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseResource>> getItems() {
        return ResponseEntity.ok(service.getItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseResource> getItemById(@PathVariable("id")UUID id) {
        return ResponseEntity.ok(service.getItemById(id));
    }
}
