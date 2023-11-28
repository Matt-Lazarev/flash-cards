package com.lazarev.flashcards.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private final Map<String, String> validationErrors;

    public ValidationException(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
