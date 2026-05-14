package com.commonlib.security;

import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtTokenValidator {

    private final RSAPublicKey  publicKey;
    private final String expectedIssuer;

    // Constructor accepts Resource (from classpath/file) and issuer string
    public JwtTokenValidator(RSAPublicKey  publicKey, String expectedIssuer) {
        this.publicKey = publicKey;
        this.expectedIssuer = expectedIssuer;
    }

    public String getExpectedIssuer() {
        return expectedIssuer;
    }

    // Validate JWT token and check issuer
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            if (expectedIssuer != null && !expectedIssuer.isBlank()) {
                return expectedIssuer.equals(claims.getBody().getIssuer());
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract claims from JWT
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
}
