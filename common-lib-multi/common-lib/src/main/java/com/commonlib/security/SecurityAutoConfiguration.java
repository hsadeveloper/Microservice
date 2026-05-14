package com.commonlib.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

//@AutoConfiguration
//@ConfigurationProperties(prefix = "security")
//@ConditionalOnMissingBean(SecurityFilterChain.class)
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

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
	                                               JwtTokenValidator validator) throws Exception {

	    return http
	            .csrf(csrf -> csrf.disable())

	            .sessionManagement(session ->
	                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

	            .authorizeHttpRequests(auth -> auth

	                    .requestMatchers(
	                    		"/error",
	                            "/api/public/**",
	                            "/v3/api-docs/**",
	                            "/swagger-ui/**",
	                            "/swagger-ui.html",
	                            "/auth/**"
	                            //"/api/payments/**",
	                            //"/api/payments/create-payment-intent"
	                    ).permitAll()

	                    .requestMatchers("/hello").hasRole("ADMIN")

	                    .requestMatchers("/payment/config").hasRole("ADMIN")
	                    .requestMatchers("/api/payments/create-payment-intent").hasRole("ADMIN")

	                    .anyRequest().authenticated()
	            )

	            .addFilterBefore(
	                    new JwtAuthenticationFilter(validator),
	                    UsernamePasswordAuthenticationFilter.class
	            )

	            .build();
	}
	

}
