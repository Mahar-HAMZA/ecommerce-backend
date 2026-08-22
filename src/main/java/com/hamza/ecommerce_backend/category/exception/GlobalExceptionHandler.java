package com.hamza.ecommerce_backend.category.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<CategoryExceptionResponse> handle(CategoryAlreadyExistsException ex) {
        //return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        CategoryExceptionResponse response=
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> notFound(CategoryNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> generalException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
