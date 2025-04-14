package org.example.postservice.controllers;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.postservice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import qwerdsa53.shared.model.ExceptionBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ExceptionBody handleConstraintViolation(ConstraintViolationException e) {
        log.error(e.getMessage());
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        exceptionBody.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage()
                )));
        return exceptionBody;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleAuthenticationException(AuthenticationException e) {
        return new ExceptionBody("Authentication failed");
    }

    @ExceptionHandler(GetPostException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleGetPostException(GetPostException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(PostSaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handlePostSaveException(PostSaveException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handlePostNotFoundException(PostNotFoundException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(PostDeleteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handlePostDeleteException(PostDeleteException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(PostLikeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handlePostLikeException(PostLikeException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(FeedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleFeedException(FeedException e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ExceptionBody(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return new ExceptionBody("Internal error");
    }
}
