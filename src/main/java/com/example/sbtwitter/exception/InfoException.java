package com.example.sbtwitter.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@Getter
public abstract class InfoException extends RuntimeException {
    private final int httpCode;
    private final int errorCode;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
