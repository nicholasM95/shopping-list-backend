package io.ordinajworks.shoppinglist.item.advisor;

import io.ordinajworks.shoppinglist.item.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ItemAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
