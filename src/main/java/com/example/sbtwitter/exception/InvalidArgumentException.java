package com.example.sbtwitter.exception;

public class InvalidArgumentException extends InfoException {
    private final static int httpCode = 400;
    private final static int errorCode = 100;
    public InvalidArgumentException(String message) {
        super(httpCode, errorCode, message);
    }
}
