package com.lazarev.flashcards.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthRequest(
        String username,
        String email,
        char[] password) { }
