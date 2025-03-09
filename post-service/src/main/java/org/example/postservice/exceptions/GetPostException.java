package org.example.postservice.exceptions;

public class GetPostException extends RuntimeException {
    public GetPostException(String message) {
        super(message);
    }
}
