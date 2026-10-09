package com.hamza.ecommerce_backend.cart.exception;

public class UnauthorizedCartAccessException extends RuntimeException {
    public UnauthorizedCartAccessException(String message) {
        super(message);
    }
}
