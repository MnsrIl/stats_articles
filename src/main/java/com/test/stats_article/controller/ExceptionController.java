package com.test.stats_article.controller;

import com.test.stats_article.exception.PlatformException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PlatformException.class)
    public ResponseEntity<Map<String, String>> handleException(PlatformException platformException) {
        Map<String, String> errorResponse = Map.of("error", platformException.getMessage());
        return new ResponseEntity<>(errorResponse, platformException.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException validationException) {
        Map<String, String> errorMap = new HashMap<>();

        validationException.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return Map.of("error", errorMap);
    }
}
