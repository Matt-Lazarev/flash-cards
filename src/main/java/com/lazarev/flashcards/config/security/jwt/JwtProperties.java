package com.lazarev.flashcards.config.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jwt.config")
public class JwtProperties {
    public static final String TOKEN_PREFIX = "Bearer";

    private String secret;
    private int tokenExpirationMs;
}
