package com.lazarev.flashcards.config.security.jwt;

import com.lazarev.flashcards.dto.auth.AuthResponse;
import com.lazarev.flashcards.dto.common.ErrorResponse;
import com.lazarev.flashcards.exception.ApplicationException;
import com.lazarev.flashcards.service.util.FilterUtil;
import com.lazarev.flashcards.service.util.JwtUtil;
import com.lazarev.flashcards.statics.ErrorResponses;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Set<String> ALLOWED_URLS = Set.of("/api/v1/auth/login", "/api/v1/auth/register");

    private final JwtUtil jwtUtil;
    private final FilterUtil filterUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse res,
            FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(req);
        if (token != null) {
            try {
                AuthResponse userInfo = jwtUtil.validateToken(token);
                Authentication auth = new UsernamePasswordAuthenticationToken(userInfo.username(), null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (ApplicationException e) {
                SecurityContextHolder.clearContext();

                ErrorResponse errorResponse = ErrorResponses.INVALID_JWT_TOKEN;
                filterUtil.sendErrorResponse(errorResponse, HttpStatus.UNAUTHORIZED, res);
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String method = request.getMethod();
        String url = request.getRequestURI();

        return HttpMethod.POST.name().equals(method) && ALLOWED_URLS.contains(url);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] headerElements = header.split(" ");
            if (headerElements.length == 2 && JwtProperties.TOKEN_PREFIX.equals(headerElements[0])) {
                return headerElements[1];
            }
        }
        return null;
    }
}
