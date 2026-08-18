package com.hamza.ecommerce_backend.category.exception;

public class CategoryAlreadyExistsException extends RuntimeException{

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
