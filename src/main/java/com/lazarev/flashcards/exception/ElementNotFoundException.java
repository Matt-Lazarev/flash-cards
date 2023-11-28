package com.lazarev.flashcards.exception;

import com.lazarev.flashcards.dto.common.ErrorResponse;
import org.springframework.http.HttpStatus;

public class ElementNotFoundException extends ApplicationException {
    public ElementNotFoundException(String operation, String message) {
        super(new ErrorResponse(operation, message, HttpStatus.NOT_FOUND));
    }
}
