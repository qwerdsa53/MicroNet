package org.example.postservice.exceptions;

public class PostSaveException extends RuntimeException {
    public PostSaveException(String message) {
        super(message);
    }
}
