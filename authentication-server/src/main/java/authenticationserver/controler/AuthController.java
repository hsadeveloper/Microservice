package authenticationserver.controler;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import authenticationserver.entity.User;
import authenticationserver.service.TokenService;




@RestController
@RequestMapping("/auth")
public class AuthController {
   

	private final TokenService tokenService;
	

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
	
	

    @GetMapping("/token")
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
    public String token(@RequestBody User user) {
    	System.out.println("=======> /token POST endpoint HIT "+ user.getUsername());
        
     
        return "Null";
    }

	
	
	
	

   
}
