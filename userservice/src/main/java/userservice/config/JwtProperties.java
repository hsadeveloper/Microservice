package userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private String secretKey;
    private long expirationTime;
    
	public JwtProperties(String secretKey, long expirationTime) {
		super();
		this.secretKey = secretKey;
		this.expirationTime = expirationTime;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}
    
    
   
}
