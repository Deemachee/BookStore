package ru.learnup.bookstore.controlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionProcessor {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        List<String> stackTrace = Arrays.stream(ex.getStackTrace())
                .map(Objects::toString)
                .collect(Collectors.toList());
        return new ResponseEntity<>(stackTrace, HttpStatus.BAD_REQUEST);
    }
}
