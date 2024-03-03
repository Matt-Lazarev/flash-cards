package com.lazarev.flashcards.service.validation;

import com.lazarev.flashcards.dto.auth.AuthRequest;
import com.lazarev.flashcards.exception.ValidationException;
import com.lazarev.flashcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.lazarev.flashcards.service.validation.ValidationConstants.*;

@Component
@RequiredArgsConstructor
public class RegistrationRequestValidator implements Validator<AuthRequest> {
    private final UserRepository userRepository;

    @Override
    public void validate(AuthRequest authRequest) {
        Map<String, String> errors = new LinkedHashMap<>();

        String username = authRequest.username();
        String email = authRequest.email();
        char[] password = authRequest.password();

        userRepository.findByUsername(username)
                .ifPresentOrElse(
                        user -> errors.put("username", "User already exists"),
                        () ->   errors.putAll(isValidUsername(username))
                );

        errors.putAll(isValidEmail(email));
        errors.putAll(isValidPassword(password));

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
