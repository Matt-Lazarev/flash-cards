package com.lazarev.flashcards.dto.common;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String operation, String message, HttpStatus httpStatus) {
}
