package userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import userservice.entity.User;
import userservice.repository.UserRepository;

@RestController
@RequestMapping("/v1/user")
public class UserController {

	 @Autowired
	 private UserRepository userRepository;
 
       
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @GetMapping("/all")
    public List<User> getAllUsers() {
    	System.out.println("All end point");
    	
        return  userRepository.findAll();
    }
    
    

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
    	
    	System.out.println("Inside RegisterUser");
       // userService.registerNewUser(user);
        return "redirect:/signup?success";
    }
    
    @PreAuthorize("principal.username == 'hasan'")
	@GetMapping("/greeting")
    public String greeting() {
        return "Welcome to User service";
    }
	

       
    
}
