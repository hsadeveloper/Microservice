package authserver.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import authserver.entity.Role;
import authserver.entity.User;
import authserver.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.security.Keys;

@Service
public class UserService {
	
	// new controller advice as anew classs
	
     UserRepository  userRepository;
    
   
    public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String validateUsersAndgenerateTokens(User user)  {
    	
    System.out.println("user inside service "+user.getUsername() +"  "+user.getPassword());
    	
      Optional <User>  userObj = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
      
      if (!userObj.isPresent()) {
          throw new UsernameNotFoundException("Invalid username or password");
      }else
      {
    	  
    	  Map<String,Set<Role>> claims = new HashMap<>();
          
          Set <Role> roles = userObj.get().getRoles();
          	
          claims.put("roles", roles);
          //System.out.println("user inside check role: "+d.get("role"));             
          
          return Jwts.builder()
          .setClaims(claims)
          .setSubject(user.getUsername())
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
          .signWith(getSignInKey(), SignatureAlgorithm.HS256)
          .compact();

    	  
    	  
      }       
    }


    private Key getSignInKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8); // Don't decode
        return Keys.hmacShaKeyFor(keyBytes);
    }



	public User registerNewUser(User user) {
		userRepository.save(user);
		return null;
	}
}
