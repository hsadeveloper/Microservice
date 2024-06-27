package orderservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import orderservice.entity.User;
import orderservice.repository.UserRepository;
import orderservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
