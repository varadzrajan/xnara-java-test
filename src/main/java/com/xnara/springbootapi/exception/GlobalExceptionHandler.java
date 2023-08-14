package com.xnara.springbootapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException ex) {
        return ((BodyBuilder) ResponseEntity.notFound()).body(ex.getMessage());
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<String> handleExternalApiException(ExternalApiException ex) {
        return ResponseEntity.badRequest().body("External API Error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(500).body("An internal server error occurred.");
    }
}

