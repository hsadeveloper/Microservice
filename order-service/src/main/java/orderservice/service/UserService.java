package orderservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import orderservice.entity.Role;
import orderservice.entity.User;
import orderservice.entity.UserRole;
import orderservice.repository.UserRepository;

@Service
public class UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserService() {
        // Default constructor
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.warn("User with username {} already exists.", user.getUsername());
        } else {
            Set<UserRole> userRoles = new HashSet<>();
            Role localRole = new Role();
            localRole.setRoleId(1); // Set the role ID as needed
            userRoles.add(new UserRole(user, localRole));
            user.setUserRoles(userRoles);

            localUser = userRepository.save(user);
        }

        return localUser;
    }

//    public User getUserByUserName(String name) {
//    	System.out.println("username "+name);
//         User user = userRepository.searchByemail(name);
//         System.out.println("user--> " +user);
//         return user;
//    }
//    
    
    
   @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public User searchByemail(String email) {
		// TODO Auto-generated method stub
		System.out.println("email ..."+email);
        User user = userRepository.searchByEmail(email);
        System.out.println("user--> " +user);
        return user;
	}

}
