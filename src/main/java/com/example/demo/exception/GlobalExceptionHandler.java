package com.example.demo.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleConflictException(DuplicateResourceException ex) {
        String error = "Resource with exception Conflict";
        return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, error, ex));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleUnAuthorizedException(ResourceNotFoundException ex) {
        String error = "Resource Not Found";
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, error, ex));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleUnAuthorizedException(IllegalArgumentException ex) {
        String error = "Parameters Cannot be null or Empty";
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}