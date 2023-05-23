package com.test.stats_article.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PlatformException extends RuntimeException {
    private final HttpStatus status;

    protected PlatformException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
