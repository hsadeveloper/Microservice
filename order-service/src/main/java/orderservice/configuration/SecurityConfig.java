package orderservice.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import orderservice.entity.User;
import orderservice.service.MyUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

   
	private final MyUserDetailsService customUserDetailsService;
	

	public SecurityConfig(MyUserDetailsService customUserDetailsService) {
		super();
		
		this.customUserDetailsService = customUserDetailsService;
	}




	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("inside configuration "+auth);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
  
	
	

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("inside security config");
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttp -> {
                    authorizeHttp
                             .requestMatchers("/v1/user/add").permitAll()
//                             .requestMatchers("/v1/user/search/**").permitAll()
                            //.requestMatchers("/v1/user/").permitAll()
                            .requestMatchers("/favicon.svg").permitAll()
                            .requestMatchers("/css/*").permitAll()
                            .requestMatchers("/error").permitAll()
                            .anyRequest().authenticated(); // Ensure all other requests are authenticated
                })
                .formLogin(withDefaults()) // Enable form login with default settings
                .logout((logout) -> logout.permitAll())
                .build();
    }

	
	


  
}