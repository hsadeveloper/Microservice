package authserver.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key_here"; // Replace with secure key
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 10; // 10 hours

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claims
        		
        		extract a specific claim
        	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        	        final Claims claims = extractAllClaims(token);
        	        return claimsResolver.apply(claims);
        	    }

        	    // Generate JWT token
        	    public String generateToken(String username) {
        	        Map<String, Object> claims = new HashMap<>();
        	        return createToken(claims, username);
        	    }

        	    // Validate JWT token
        	    public boolean validateToken(String token, String username) {
        	        final String extractedUsername = extractUsername(token);
        	        return (extractedUsername.equals(username) && !isTokenExpired(token));
        	    }

        	    // Check if token is expired
        	    private boolean isTokenExpired(String token) {
        	        return extractExpiration(token).before(new Date());
        	    }

        	    // Create JWT token with claims, subject, issued date, expiry, and signature
        	    private String createToken(Map<String, Object> claims, String subject) {
        	        return Jwts.builder()
        	                .setClaims(claims)
        	         