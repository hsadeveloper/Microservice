package authenticationserver.controler;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import authenticationserver.entity.User;
import authenticationserver.service.TokenService;
import authenticationserver.service.UserService;



@RestController
@RequestMapping("/auth")
public class AuthController {
   

	private final TokenService tokenService;
	private final UserService  userService;
	private final JwtDecoder jwtDecoder;
    
    public AuthController(TokenService tokenService, UserService userService, JwtDecoder jwtDecode) {
		super();
		this.tokenService = tokenService;
		this.userService = userService;
		this.jwtDecoder=jwtDecode;
	}

    
    
    @PostMapping("/decode")
    public ResponseEntity<String> decode(@RequestBody String token) {
    	System.out.println("=======> /token POST endpoint decode*****");
    	userService.decodeAndPrintToken(token.trim());
        return ResponseEntity.ok("Token decoded, check logs.");
    }
    
   

	@GetMapping("/hello")
    public String hello() {
        System.out.println("Token requested for user: '{}'");
       
        return "token";
    }


    @PostMapping("/token")
    public String token(Authentication authentication) {
    	System.out.println("=======> /token POST endpoint HIT");
        if (authentication == null) {
            System.out.println("Authentication is NULL");
            throw new RuntimeException("Unauthorized");
        }
        System.out.println("Authenticated user: " + authentication.getName());
        return tokenService.generateToken(authentication);
    }
    
    
    @PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		System.out.println("user  "+user.getUsername() +"  "+user.getPassword());
	    // Check if user exists, hash password, save to DB
	    User userObj = userService.signUp(user);
	    return ResponseEntity.ok(userObj);
	}
	
	

   
}
