package com.cts.auditservice.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
/**
 * Service for handling JWT token operations.
 * <p>
 * Provides methods to validate tokens and extract claims
 * such as username and role.
 */
@Service
public class JwtService {

    /**
     * Secret key used for signing and validating tokens.
     */
    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Returns the signing key for JWT processing.
     *
     * @return the {@link SecretKey}
     */
    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token
     * @throws io.jsonwebtoken.JwtException if the token is invalid or expired
     */
    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    /**
     * Returns the username from the token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Returns the role from the token.
     * <p>
     * Retrieved from the {@code role} claim.
     *
     * @param token the JWT token
     * @return the user role
     */
    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }
}