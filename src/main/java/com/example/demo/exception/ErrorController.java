package com.example.demo.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;



@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ErrorController {


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

    /*@ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex) {
        String error = "Invalid User Credentials";
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, error, ex));
    }*/
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /*@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(HttpMessageNotReadableException exception, HttpServletRequest request){
        return new ResponseEntity<String>(exception+"\n"+"\n"+request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientAuthorizationException.class)
    public ResponseEntity<String> handleException(ClientAuthorizationException exception, HttpServletRequest request){
        return new ResponseEntity<String>(exception+"\n"+"\n"+request, HttpStatus.BAD_REQUEST);
    }*/
}
