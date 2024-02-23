package com.github.pavelvil.jwtexample.exception;

public class AuthException extends RuntimeException {
    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }
}
