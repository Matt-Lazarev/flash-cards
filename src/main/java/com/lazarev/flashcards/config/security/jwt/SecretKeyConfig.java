package com.lazarev.flashcards.config.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class SecretKeyConfig {
    private final JwtProperties jwtProperties;

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecret()));
    }
}
