package com.example.sbtwitter.exception;

public class UnauthorisedException extends InfoException {

    private final static String MESSAGE = "You are not authorised to perform this action";
    private final static int httpCode = 401;
    private final static int errorCode = 101;
    public UnauthorisedException() {
        super(httpCode, errorCode, MESSAGE);
    }
}
