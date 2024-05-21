package com.example.sbtwitter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(400).body(getErrorDetails(
                new InvalidArgumentException(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(400).body(getErrorDetails(
                new InvalidArgumentException("Invalid value for parameter: " + ex.getName())));
    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<Map<String,Object>> handleUnauthorisedException(UnauthorisedException ex) {
        return ResponseEntity.status(401).body(getErrorDetails(ex));
    }

    @ExceptionHandler(TweetNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleTweetNotFoundException(TweetNotFoundException ex) {
        return ResponseEntity.status(404).body(getErrorDetails(ex));
    }

    @ExceptionHandler(UnauthorisedTweetDeletionException.class)
    public ResponseEntity<Map<String,Object>> handleUnauthorisedTweetDeletionException(UnauthorisedTweetDeletionException ex) {
        return ResponseEntity.status(403).body(getErrorDetails(ex));
    }

    private Map<String, Object> getErrorDetails(InfoException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("httpCode", ex.getHttpCode());
        errorDetails.put("errorCode", ex.getErrorCode());
        errorDetails.put("message", ex.getMessage());
        return errorDetails;
    }
}
