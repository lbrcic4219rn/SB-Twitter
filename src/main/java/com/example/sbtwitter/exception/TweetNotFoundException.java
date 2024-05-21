package com.example.sbtwitter.exception;

public class TweetNotFoundException extends InfoException {

    private final static String MESSAGE = "Tweet not found";
    private final static int httpCode = 404;
    private final static int errorCode = 104;

    public TweetNotFoundException() {
        super(httpCode, errorCode, MESSAGE);
    }
}
