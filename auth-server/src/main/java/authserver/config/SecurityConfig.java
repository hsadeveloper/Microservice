package authserver.config;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import authserver.service.RsaKeyProperties;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SecurityConfig {

	private final RsaKeyProperties jwtConfigProperties;

	public SecurityConfig(RsaKeyProperties jwtConfigProperties) {
		this.jwtConfigProperties = jwtConfigProperties;
	}
	
	
	
	@Bean
	public InMemoryUserDetailsManager users() {
	    return new InMemoryUserDetailsManager(
	            User.withUsername("hasan")
	                .password("{noop}123")
//	                .authorities("ROLE_USER")  // <-- add authorities
	                .build()
	    );
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource1() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("https://localhost:3000"));
	    configuration.setAllowedHeaders(List.of("*"));
	    configuration.setAllowedMethods(List.of("GET", "POST"));  // <-- add POST here
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean
	public SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .securityMatcher(new AntPathRequestMatcher("/auth/token"))
	        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .csrf(AbstractHttpConfigurer::disable)
	        .httpBasic(withDefaults())  // <-- this enables Basic Auth here
	        .exceptionHandling(ex -> ex
	            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
	            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
	        );

	    return http.build();
	}


	@Bean
	public JwtEncoder jwtEncoder() {
	    JWK jwk = new RSAKey.Builder(jwtConfigProperties.getPublicKey())
	        .privateKey(jwtConfigProperties.getPrivateKey())
	        .build();
	    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
	    return new NimbusJwtEncoder(jwks);
	}

	@Bean
	public JwtDecoder jwtDecoder() {
	    return NimbusJwtDecoder.withPublicKey(jwtConfigProperties.getPublicKey()).build();
	}


	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("https://localhost:8010"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowedMethods(List.of("GET"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}