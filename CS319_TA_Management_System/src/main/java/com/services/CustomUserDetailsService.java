package com.services;

import java.util.Collection;
import java.util.Collections;

import javax.management.RuntimeErrorException;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entities.Admin;
// Ensure the correct package path for RolesEnum
import com.entities.Roles; // Update this path if RolesEnum is in a different package
import com.repositories.UserRepository;
import com.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.entities.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        System.out.println("User found: " + user.getUsername());
            return new CustomUserDetails(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().name()
            );
    }

} 



