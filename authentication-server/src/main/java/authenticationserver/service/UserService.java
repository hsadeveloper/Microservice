package authenticationserver.service;



import java.util.HashSet;
import java.util.Set;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import authenticationserver.entity.Role;
import authenticationserver.entity.User;
import authenticationserver.repository.RoleRepository;
import authenticationserver.repository.UserRepository;

@Service
public class UserService {
	
	// new controller advice as anew classs
	
     UserRepository  userRepository;
     RoleRepository  roleRepository;
     TokenService   tokenService;
     private final JwtDecoder jwtDecoder;
    
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, TokenService   tokenService, JwtDecoder jwtDecoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.tokenService = tokenService;
		this.jwtDecoder = jwtDecoder;
	}



    public void decodeAndPrintToken(String token) {
        Jwt decodedJwt = jwtDecoder.decode(token);

        System.out.println("Subject: " + decodedJwt.getSubject());
//        System.out.println("Issuer: " + decodedJwt.getIssuer());
//        System.out.println("Issued At: " + decodedJwt.getIssuedAt());
        System.out.println("Expiration: " + decodedJwt.getExpiresAt());
        System.out.println("Scope: " + decodedJwt.getClaim("scope"));

        System.out.println("All claims: " + decodedJwt.getClaims());
    }


    public User signUp(User user) {
        Set<Role> inputRoles = user.getRoles();
        Set<Role> resolvedRoles = new HashSet<>();

        if (inputRoles == null || inputRoles.isEmpty()) {
            // Default role if none provided
            Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role 'USER' not found"));
            
            defaultRole.setUser(user); // 🔥 set user back-reference
            
            resolvedRoles.add(defaultRole);
        } else {
            for (Role inputRole : inputRoles) {
                // Optionally resolve from DB or trust input
                inputRole.setUser(user); // 🔥 set user reference
                resolvedRoles.add(inputRole);
            }
        }

        user.setRoles(resolvedRoles);
        return userRepository.save(user); // Cascade saves roles
    }



    


}
