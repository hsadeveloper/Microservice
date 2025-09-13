package userservice.config;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import userservice.entity.Role;
import userservice.entity.User;
import userservice.repository.RoleRepository;
import userservice.repository.UserRepository;

@Service
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        initRoles();
    	System.out.println("i'm heeeeeer create usersssss"+userRepository.count());
        if (userRepository.count() == 0) {
        	System.out.println("i'm heeeeeer create usersssss");
        // Create users with roles
        createUser("admin", "Admin", "admin@example.com", passwordEncoder.encode("password"), "ADMIN");
        createUser("hasan", "User", "user@example.com", passwordEncoder.encode("1234"), "USER");
        createUser("hason", "hasanain", "admin1@example.com", passwordEncoder.encode("1234"), "USER");
       }
    }
    
    
    

    private void createUser(String username, String name, String email, String password, String roleId) { // Renamed parameter for clarity
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setFirstName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        
        // Retrieve the Role object from the database
        Optional<Role> roleOptional = roleRepository.findById(roleId); // Assuming RoleRepository has findById for the roleId (which is the actual ID, e.g., "ADMIN", "USER")
        
        if (roleOptional.isPresent()) {
            Role foundRole = roleOptional.get();
            user.setRoles(Arrays.asList(foundRole)); // Set the role for the user
        } else {
            System.err.println("Role with ID " + roleId + " not found. User " + username + " will not have this role.");
            // You might want to throw an exception or handle this more robustly
            user.setRoles(new ArrayList<>()); // Set an empty list if role not found
        }
        
        System.out.println("i'm heeeeeer");
        System.out.println(user.getFirstName());
        userRepository.save(user);
    }
    
    private void initRoles() {
        // Check if roles already exist
        if (roleRepository.count() == 0) {
        	
        	System.out.println("i'm heeeeeer create roles");
            // Create and save roles
            Role adminRole = new Role();
            adminRole.setRoleId("ADMIN");
            adminRole.setName("Admin");
            adminRole.setDescription(" Administrator role");

            Role userRole = new Role();
            userRole.setRoleId("USER");
            userRole.setName("User");
            userRole.setDescription("Normal user role");
            
            

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }
}