package authenticationserver.config;



import static org.springframework.security.config.Customizer.withDefaults;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

import authenticationserver.entity.CustomUserDetailsService;
import authenticationserver.entity.RsaKeyProperties;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties jwtConfigProperties;

    

    private final CustomUserDetailsService userDetailsService;
    
    public SecurityConfig(RsaKeyProperties jwtConfigProperties, CustomUserDetailsService userDetailsService) {
		super();
		this.jwtConfigProperties = jwtConfigProperties;
		 this.userDetailsService = userDetailsService;
	}

 
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
            .getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .and()
            .build();
    }
    

  
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Only the origin (scheme + host + port)
        configuration.setAllowedOrigins(List.of("http://localhost:8010"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Priority chain for /auth/token
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/decode").permitAll()
                .requestMatchers("/auth/signup").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .oauth2ResourceServer(resourceServer -> 
                resourceServer.jwt(withDefaults())
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
            .build();
    }

    
    


	/*
	 * This was added via PR (thanks to @ch4mpy)
	 * This will allow the /token endpoint to use basic auth and everything else uses the SFC above
	 */
    @Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean
	SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.securityMatcher(new AntPathRequestMatcher("/auth/token"))
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(AbstractHttpConfigurer::disable)
				.exceptionHandling(ex -> {
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				})
				.httpBasic(withDefaults())
				.build();
	}
        

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(jwtConfigProperties.publicKey())
            .privateKey(jwtConfigProperties.privateKey())
            .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(jwtConfigProperties.publicKey()).build();
    }
}
