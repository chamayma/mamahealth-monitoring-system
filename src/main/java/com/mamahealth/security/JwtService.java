package com.mamahealth.security;


import io.jsonwebtoken.Jwts;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(Long userId, String role) {

        return Jwts.builder()

                .subject(userId.toString())

                .claim("role", role)

                .issuedAt(new Date())

                .expiration(new Date(
                        System.currentTimeMillis() + jwtExpiration))

                .signWith(getSigningKey())

                .compact();

    }

}