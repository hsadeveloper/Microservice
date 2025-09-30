package authserver.controler;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import authserver.config.RestTemplateConfig;
import authserver.entity.User;
import authserver.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
   

	@Autowired
	private RestTemplate restTemplate;
	
	private UserService  userService;
		
    public AuthController(UserService userService,RestTemplateConfig  restTemplateConfig ) {
		super();
		this.userService = userService;
	}

   

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@Valid @RequestBody User user) {

        System.out.println("user  " + user.getUsername() + "  " + user.getPassword());

        String token = userService.validateUsersAndgenerateTokens(user);

        if (token != null && !token.isEmpty()) {
            try {
            	System.out.println("in *********************** ");
                // Replace with real userId logic
                String userId = String.valueOf(user.getId()); // or user.getUsername(), etc.
                String url = "http://localhost:2085/cart?userId=" + userId;

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                HttpEntity<Void> entity = new HttpEntity<>(headers);
                
                System.out.println("in *********************** 2222222222222");
                ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
                );

                System.out.println("response ************* "+response);
                // Optional: return both token and cart info
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("token", token);
                responseBody.put("cart", response.getBody());

                return new ResponseEntity<>(responseBody, HttpStatus.CREATED);

            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Token generated, but cart service failed", HttpStatus.PARTIAL_CONTENT);
            }
        }

        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }


	

	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		System.out.println("user  "+user.getUsername() +"  "+user.getPassword());
	    // Check if user exists, hash password, save to DB
	    User userObj = userService.registerNewUser(user);
	    return ResponseEntity.ok("User registered successfully");
	}
	
	
	

   
}
