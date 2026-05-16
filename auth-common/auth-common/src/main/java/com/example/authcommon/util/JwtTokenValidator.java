package com.example.authcommon.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenValidator {
   // @Value("${application.security.jwt.secret-key}")
    private final String secretKey="VHl6zXp0f4Vn2dK8yqM5sA7bE9uG0iL1oP3rQ6jW4cZ2xT1vY0eU8dF7hG3";
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    private final Key key = Keys.hmacShaKeyFor(keyBytes);

    public boolean validateToken(String token) {
        System.out.println("inside validate");
        try {
            parseClaims(token);
            System.out.println("return true");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims getAllClaims(String token) {
        return parseClaims(token);
    }

    private Claims parseClaims(String token) {
        System.out.println("Inside parse claim");
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
