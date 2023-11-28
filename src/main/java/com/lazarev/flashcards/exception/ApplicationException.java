package com.lazarev.flashcards.exception;

import com.lazarev.flashcards.dto.common.ErrorResponse;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public ApplicationException(ErrorResponse errorResponse, Throwable cause) {
        super(errorResponse.message(), cause);
        this.errorResponse = errorResponse;
    }

    public ApplicationException(ErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }
}
