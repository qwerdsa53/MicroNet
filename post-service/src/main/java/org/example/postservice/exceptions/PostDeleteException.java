package org.example.postservice.exceptions;

public class PostDeleteException extends RuntimeException {
    public PostDeleteException(String message) {
        super(message);
    }
}
