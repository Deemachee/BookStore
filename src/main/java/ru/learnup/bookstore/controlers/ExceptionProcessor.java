package ru.learnup.bookstore.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.learnup.bookstore.BookStoreApplication;

import javax.persistence.EntityExistsException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionProcessor {

    static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        List<String> stackTrace = Arrays.stream(ex.getStackTrace())
                .map(Objects::toString)
                .collect(Collectors.toList());
        log.error("",ex);
        return new ResponseEntity<>(stackTrace, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDenied(AccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
