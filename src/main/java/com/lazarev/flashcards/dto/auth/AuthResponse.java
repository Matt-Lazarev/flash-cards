package com.lazarev.flashcards.dto.auth;

public record AuthResponse(
        String username,
        String email,
        String token) { }
