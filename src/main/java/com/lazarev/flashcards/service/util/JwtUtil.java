package com.lazarev.flashcards.service.util;

import com.lazarev.flashcards.config.security.jwt.JwtProperties;
import com.lazarev.flashcards.dto.auth.AuthResponse;
import com.lazarev.flashcards.entity.ApplicationUser;
import com.lazarev.flashcards.exception.ApplicationException;
import com.lazarev.flashcards.statics.ErrorResponses;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public String createToken(ApplicationUser user) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + jwtProperties.getTokenExpirationMs());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .claim("email", user.getEmail())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public AuthResponse validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new AuthResponse(claims.getSubject(), (String) claims.get("email"), token);
        } catch (Exception e) {
            throw new ApplicationException(ErrorResponses.INVALID_JWT_TOKEN, e);
        }
    }
}
