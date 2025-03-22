package com.edwin.Popcorn_Pal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
            try {
                // Split JWT into parts: header, payload, signature
                String[] parts = jwt.split("\\.");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid JWT format");
                }

                // Decode the payload (second part)
                String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                // Extract username (assuming "sub" claim holds the username)
                username = extractUsernameFromPayload(payload);

                // Note: This skips signature verification for simplicity
                // In production, you should verify the signature with the secret key
            } catch (Exception e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, null); // No authorities needed for this example
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            logger.info("Authenticated user: {}", username);
        } else if (username == null && header != null) {
            logger.warn("Failed to authenticate user with token: {}", jwt);
        }

        chain.doFilter(request, response);
    }

    private String extractUsernameFromPayload(String payload) {
        // Simple JSON parsing (assuming payload is {"sub":"username",...})
        String subjectKey = "\"sub\":\"";
        int start = payload.indexOf(subjectKey) + subjectKey.length();
        int end = payload.indexOf("\"", start);
        if (start > subjectKey.length() - 1 && end > start) {
            return payload.substring(start, end);
        }
        return null;
    }
}