package com.hamza.ecommerce_backend.category.exception;

public class CategoryDeletionNotAllowedException extends RuntimeException{

    public CategoryDeletionNotAllowedException(String message) {
        super(message);
    }
}
