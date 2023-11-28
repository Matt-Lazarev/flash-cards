package com.lazarev.flashcards.exception;

import com.lazarev.flashcards.dto.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ApplicationException ex) {
        ErrorResponse errorResponse = ex.getErrorResponse();

        return ResponseEntity
                .status(errorResponse.httpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpBadRequest(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Request", ex.getMessage(), BAD_REQUEST);

        return ResponseEntity
                .status(errorResponse.httpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> validationErrors = ex.getValidationErrors();

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(validationErrors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Request", ex.getMessage(), NOT_FOUND);

        ex.printStackTrace();
        return ResponseEntity
                .status(NOT_FOUND)
                .body(errorResponse);
    }
}
