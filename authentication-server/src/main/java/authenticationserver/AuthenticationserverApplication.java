package authenticationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import authenticationserver.entity.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class AuthenticationserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationserverApplication.class, args);
	
	}

}
