package com.lazarev.flashcards.service;

import com.lazarev.flashcards.dto.auth.AuthRequest;
import com.lazarev.flashcards.dto.auth.AuthResponse;
import com.lazarev.flashcards.entity.ApplicationUser;
import com.lazarev.flashcards.exception.ValidationException;
import com.lazarev.flashcards.repository.UserRepository;
import com.lazarev.flashcards.service.mapper.UserMapper;
import com.lazarev.flashcards.service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest authRequest) {
        String username = authRequest.username();
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(()->new ValidationException(
                        Map.of("username", "User does not exist"))
                );

        String token = jwtUtil.createToken(user);
        return userMapper.toAuthResponse(user, token);
    }

    public AuthResponse register(AuthRequest authRequest) {
        ApplicationUser user = userMapper.toApplicationUser(authRequest);

        String encryptedPassword = passwordEncoder.encode(CharBuffer.wrap(authRequest.password()));
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        return userMapper.toAuthResponse(user, null);
    }
}