package com.example.authcommon.service;

import com.example.authcommon.repository.UserDetailsRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDetailsRepository  userDetailsRepository;

    public CustomUserDetailsService(UserDetailsRepository  userDetailsRepository){
        this.userDetailsRepository = userDetailsRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.authcommon.entity.UserDetails> userDetailsOpt = userDetailsRepository.findByUsername(username);
       if(userDetailsOpt.isEmpty()){
           throw new UsernameNotFoundException("User not found");
       }
        com.example.authcommon.entity.UserDetails userDetails = userDetailsOpt.get();
        return  new CustomUserDetails(
                userDetails.getId(),
                userDetails.getUserName(),
                userDetails.getPassword(),
                List.of(new SimpleGrantedAuthority(userDetails.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.joining()))));
    }
}
