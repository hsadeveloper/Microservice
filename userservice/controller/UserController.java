package com.org.userservice.controller;

import java.awt.PageAttributes.MediaType;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.org.userservice.entity.User;
import com.org.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

	private final UserService userService;
 
	@Autowired
	   RestTemplate restTemplate;
   

    public UserController(UserService userService ) {
		super();
		this.userService = userService;
	
	}
    
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        userService.registerNewUser(user);
        return "redirect:/signup?success";
    }

	@GetMapping("/")
    public String greeting() {
        return "Welcome to User service";
    }
	

    @GetMapping("/all")
    public List<User> getUsers() {
        System.out.println("Fetching all users");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + auth.getPrincipal());
        
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println("User details: " + userDetails);
            System.out.println("role: " + userDetails.getAuthorities());
        }
    
  
        return userService.getAllUsers();
    }
    
//    
//    @GetMapping("/get")
//    public ResponseEntity<String> redirectToAnotherService() {
//    	System.out.println("Inside getProducts ");
////     	String url = "http://localhost:1717/product/all";
////    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
////        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());	  	
//		return null;
//    	
//       
//    }
    
    @GetMapping("/products")
    public ResponseEntity<String> getProducts() {
    	String url = "http://localhost:1717/product/all";
    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    	return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    	
    }

    
    // Dynamic username fetching using @RequestParam
    @GetMapping("/search/{email}")
    public User getUserByUserName(@PathVariable("email") String email) {
        System.out.println("searchin email: " + email);
        System.out.println("Fetching all users");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + auth.getPrincipal());
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println("User details: " + userDetails);
            System.out.println("role: " + userDetails.getAuthorities());
        }
    
        return userService.searchByemail(email);
    }

    @PostMapping("/add")
    public User createUser(@RequestBody User user) {
        System.out.println("Posting user: " + user);
        return userService.createUser(user);
    }
    
    
    
    
}
