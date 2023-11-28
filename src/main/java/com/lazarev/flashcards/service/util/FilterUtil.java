package com.lazarev.flashcards.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazarev.flashcards.dto.common.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class FilterUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public void sendErrorResponse(ErrorResponse errorResponse, HttpStatus httpStatus,
                                   HttpServletResponse response){
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        response.getWriter()
                .write(OBJECT_MAPPER
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(errorResponse));
    }
}
