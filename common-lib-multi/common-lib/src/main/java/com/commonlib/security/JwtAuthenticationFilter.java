package com.commonlib.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator validator;

    public JwtAuthenticationFilter(JwtTokenValidator validator) {
        this.validator = validator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println("Header ************************* "+header);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (validator.validateToken(token)) {
                Claims claims = validator.getClaims(token);
                String username = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);

                System.out.println("Authenticated user: " + username);
                
                // Map roles to GrantedAuthority with "ROLE_" prefix
                var authorities = roles.stream()
                        .map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + r))
                        .collect(Collectors.toList());
                
                
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        username, "", authorities);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

//                // Set authentication in SecurityContext
//                var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
//                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
    
    protected void doFilterInternalOLD (HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (validator.validateToken(token)) {
                Claims claims = validator.getClaims(token);

                List<SimpleGrantedAuthority> authorities = Collections.emptyList();
                Object rolesObj = claims.get("roles");
                if (rolesObj instanceof java.util.List) {
                    authorities = ((List<?>) rolesObj)
                            .stream()
                            .map(Object::toString)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                claims.getSubject(),
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
