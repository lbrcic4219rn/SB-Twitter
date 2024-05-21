package com.example.sbtwitter.exception;

public class UnauthorisedTweetDeletionException extends InfoException {

    private final static String MESSAGE = "You are not authorised to delete this tweet";
    private final static int httpCode = 403;
    private final static int errorCode = 103;
    public UnauthorisedTweetDeletionException() {
        super(httpCode, errorCode, MESSAGE);
    }
}
