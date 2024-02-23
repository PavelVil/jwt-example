package com.github.pavelvil.jwtexample.exception;

public class TokenParseException extends RuntimeException {
    public TokenParseException(String message) {
        super(message);
    }
}
