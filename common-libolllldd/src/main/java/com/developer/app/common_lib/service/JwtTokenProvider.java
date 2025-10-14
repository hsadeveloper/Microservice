package com.developer.app.common_lib.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenProvider {
   // @Value("${application.security.jwt.secret-key}")
    private final static String secretKey="VHl6zXp0f4Vn2dK8yqM5sA7bE9uG0iL1oP3rQ6jW4cZ2xT1vY0eU8dF7hG3";
    static byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    private final static Key key = Keys.hmacShaKeyFor(keyBytes);

    public boolean validateToken(String token) {
        System.out.println("inside validate");
        try {
        	extractClaims(token);
            System.out.println("return true");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Claims getAllClaims(String token) {
        return extractClaims(token);
    }
    
    public static Claims extractClaims(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build() // ✅ build() now works here
                .parseClaimsJws(token);

        return claimsJws.getBody();
    }

  
}