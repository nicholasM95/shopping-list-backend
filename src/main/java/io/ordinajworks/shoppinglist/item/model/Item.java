package io.ordinajworks.shoppinglist.item.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Item {
    private UUID id;
    private String name;
    private int amount;
}
