package io.ordinajworks.shoppinglist.item.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
