package com.commonlib.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

@AutoConfiguration
//@ConfigurationProperties(prefix = "security")
@EnableConfigurationProperties(RsaKeyProperties.class)
@ConditionalOnProperty(prefix = "common.security.jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SecurityAutoConfiguration {

    private final KeyLoader keyLoader;

    @Autowired
    public SecurityAutoConfiguration(KeyLoader keyLoader) {
        this.keyLoader = keyLoader;
    }

	
	@Bean
	public JwtTokenValidator jwtTokenValidator(RsaKeyProperties props) {
		RSAPublicKey publicKey = keyLoader.loadPublicKey("classpath:public_key.pem");
		return new JwtTokenValidator(publicKey, props.getIssuer());
	}

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenValidator validator) throws Exception {
//	    return http
//	            .csrf(csrf -> csrf.disable())
//	            .authorizeHttpRequests(auth -> auth
//	                    .requestMatchers("/hello").hasRole("ADMIN")
//	                    .requestMatchers("/cart/hello").hasRole("ADMIN")
//	                    .anyRequest().permitAll())
//	            .addFilterBefore(new JwtAuthenticationFilter(validator),
//	                             org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
//	            .build();
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenValidator validator) throws Exception {
	    return http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	            		.requestMatchers("/api/public/**",
					    		"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
					    		"/auth/**").permitAll()
	                    .requestMatchers("/hello").hasRole("ADMIN")	                    
	                    .anyRequest().authenticated())
	            .addFilterBefore(new JwtAuthenticationFilter(validator),
	                             org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
	            .build();
	}
	
	
	
}
