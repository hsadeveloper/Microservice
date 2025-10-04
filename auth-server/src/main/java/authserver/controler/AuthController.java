package authserver.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import authserver.entity.User;
import authserver.repository.TokenService;
import authserver.service.UserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {
   

	@Autowired
	private RestTemplate restTemplate;
	
	private UserService  userService;
	
	 private final TokenService tokenService;
		
  
    
    public AuthController(RestTemplate restTemplate, UserService userService, TokenService tokenService) {
		super();
		this.restTemplate = restTemplate;
		this.userService = userService;
		this.tokenService = tokenService;
	}

    @GetMapping("/token")
    public String hello() {
        System.out.println("Token requested for user: '{}'");
       
        return "token";
    }




//
//	@PostMapping("/token")
//    public String token(Authentication authentication) {
//        System.out.println("Token requested for user: '{}'"+ authentication.getName());
//        String token = tokenService.generateToken(authentication);
//        //LOG.debug("Token granted: {}", token);
//        return token;
//    }


   

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@Valid @RequestBody User user) {

        System.out.println("user  " + user.getUsername() + "  " + user.getPassword());

        String token = userService.validateUsersAndgenerateTokens(user);

        if (token != null && !token.isEmpty()) {
          
            	System.out.println("in *********************** ");
                // Replace with real userId logic
                String userId = String.valueOf(user.getId()); // or user.getUsername(), etc.
           
            
        }
		return null;
    }
        
        // String url = "http://localhost:2085/cart?userId=" + userId;
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.setBearerAuth(token);
//                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//                HttpEntity<Void> entity = new HttpEntity<>(headers);
//                
//                System.out.println("in *********************** 2222222222222");
//                ResponseEntity<String> response = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    entity,
//                    String.class
//                );
//
//                System.out.println("response ************* "+response);
//                // Optional: return both token and cart info
//                Map<String, Object> responseBody = new HashMap<>();
//                responseBody.put("token", token);
//                responseBody.put("cart", response.getBody());
//
//                return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new ResponseEntity<>("Token generated, but cart service failed", HttpStatus.PARTIAL_CONTENT);
//            }
//        }
//
//        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
//    }


	

	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		System.out.println("user  "+user.getUsername() +"  "+user.getPassword());
	    // Check if user exists, hash password, save to DB
	    User userObj = userService.registerNewUser(user);
	    return ResponseEntity.ok("User registered successfully");
	}
	
	
	

   
}
