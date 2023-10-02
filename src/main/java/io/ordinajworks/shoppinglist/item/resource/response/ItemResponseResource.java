package io.ordinajworks.shoppinglist.item.resource.response;

import java.util.UUID;

public record ItemResponseResource(UUID id, int order, String name, int amount) {
}
