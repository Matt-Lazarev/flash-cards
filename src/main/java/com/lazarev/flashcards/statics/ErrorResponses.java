package com.lazarev.flashcards.statics;

import com.lazarev.flashcards.dto.common.ErrorResponse;
import lombok.experimental.UtilityClass;

import static org.springframework.http.HttpStatus.*;

@UtilityClass
public class ErrorResponses {
    public ErrorResponse INVALID_PASSWORD = new ErrorResponse("Authentication", "Invalid password", UNAUTHORIZED);
    public ErrorResponse USER_ALREADY_EXISTS = new ErrorResponse("Registration","User already exists", BAD_REQUEST);
    public ErrorResponse INVALID_JWT_TOKEN = new ErrorResponse("Authentication", "Invalid JWT token", UNAUTHORIZED);
    public ErrorResponse ILLEGAL_ACCESS = new ErrorResponse("Authorization", "Access to resource is forbidden", FORBIDDEN);
}
