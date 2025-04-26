package com.services;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entities.Admin;
// Ensure the correct package path for RolesEnum
import com.entities.Roles; // Update this path if RolesEnum is in a different package
import com.repositories.AdminsRepository;

@Service
public class AdminDetailsService implements UserDetailsService {
   
    private final AdminsRepository adminRepository;

    public AdminDetailsService(AdminsRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Admin admin = adminRepository.findByUsername(username)
        
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + username));
        System.out.println("Admin found: " + admin.getUsername() + ", Role: " + admin.getRole().name());
        System.out.println("Admin found: " + admin.getUsername() + ", Role: " + admin.getRole().name() +"password: " + admin.getPassword());
        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())  // bu zaten hashlenmiş olmalı!
                .roles( admin.getRole().name())  // veya .authorities(...) da kullanılabilir
                .build();
    }

} 



