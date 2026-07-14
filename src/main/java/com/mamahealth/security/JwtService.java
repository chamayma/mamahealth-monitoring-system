package com.mamahealth.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Secret signing key
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate JWT
     */
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
}

    /**
     * Extract all claims
     */
    public Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }

    /**
     * Generic claim extractor
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Extract User ID
     */
    public String extractEmail(String token) {

    return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract User Role
     */
    public String extractRole(String token) {

        return extractClaim(

                token,

                claims -> claims.get("role", String.class));
    }

    /**
     * Extract Expiration
     */
    public Date extractExpiration(String token) {

        return extractClaim(

                token,

                Claims::getExpiration);
    }

    /**
     * Check expiration
     */
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)

                .before(new Date());
    }

    /**
     * Validate JWT
     */
    public boolean isTokenValid(String token) {

        try {

            return !isTokenExpired(token);

        } catch (Exception ex) {

            return false;
        }
    }
}