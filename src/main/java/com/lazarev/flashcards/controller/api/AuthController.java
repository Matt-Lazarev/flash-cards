package com.lazarev.flashcards.controller.api;

import com.lazarev.flashcards.annotation.Validate;
import com.lazarev.flashcards.dto.auth.AuthRequest;
import com.lazarev.flashcards.dto.auth.AuthResponse;
import com.lazarev.flashcards.service.AuthService;
import com.lazarev.flashcards.service.validation.LoginRequestValidator;
import com.lazarev.flashcards.service.validation.RegistrationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Validate(LoginRequestValidator.class)
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    @Validate(RegistrationRequestValidator.class)
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.register(authRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponse);
    }
}