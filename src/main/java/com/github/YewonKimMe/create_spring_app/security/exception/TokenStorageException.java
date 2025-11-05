package com.github.YewonKimMe.create_spring_app.security.exception;

public class TokenStorageException extends RuntimeException {
    public TokenStorageException(String message) {
        super(message);
    }
    public TokenStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
