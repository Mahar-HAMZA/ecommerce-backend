package com.hamza.ecommerce_backend.user.exception;

public class LoginFailedResponseDTO extends RuntimeException {
    public LoginFailedResponseDTO(String message) {
        super(message);
    }
}
