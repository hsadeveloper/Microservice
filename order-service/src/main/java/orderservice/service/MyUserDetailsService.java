package orderservice.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import orderservice.entity.User;
import orderservice.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService  {

	
	UserRepository userRepository;
	
	
	
	public MyUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("before the username call   "+ username);
		User user = userRepository.findByUsername(username);
		
        if (user == null) {
        	System.out.println("INSIDE SER IS NUKK   "+ username);
            throw new UsernameNotFoundException(username);
        }
        System.out.println("USER IS NOT NULL  "+ user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRole().getName()))
                .collect(Collectors.toList());
        
        System.out.println("auth ==> "+ auth);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

}
