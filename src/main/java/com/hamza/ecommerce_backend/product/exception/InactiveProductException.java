package com.hamza.ecommerce_backend.product.exception;

public class InactiveProductException extends RuntimeException {
    public InactiveProductException(String message) {
        super(message);
    }
}
