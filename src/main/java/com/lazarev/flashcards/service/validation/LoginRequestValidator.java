package com.lazarev.flashcards.service.validation;

import com.lazarev.flashcards.dto.auth.AuthRequest;
import com.lazarev.flashcards.exception.ValidationException;
import com.lazarev.flashcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginRequestValidator implements Validator<AuthRequest> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void validate(AuthRequest authRequest) {
        Map<String, String> errors = new LinkedHashMap<>();

        String username = authRequest.username();
        char[] password = authRequest.password();

        userRepository.findByUsername(username)
                .ifPresentOrElse(
                        user -> errors.putAll(checkPasswordMatch(password, user.getPassword())),
                        () ->   errors.put("username", "User does not exist")
                );

        if (errors.size() != 0) {
            throw new ValidationException(errors);
        }
    }

    private Map<String, String> checkPasswordMatch(char[] rawPassword, String encodedPassword) {
        if (passwordEncoder.matches(CharBuffer.wrap(rawPassword), encodedPassword)) {
            return Map.of();
        }
        return Map.of("password", "Invalid password");
    }
}
