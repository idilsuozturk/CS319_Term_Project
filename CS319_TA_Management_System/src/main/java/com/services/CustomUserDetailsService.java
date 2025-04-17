package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entities.Admin;
import com.repositories.AdminsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   
    private final AdminsRepository adminRepository;

    public CustomUserDetailsService(AdminsRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + username));

                System.out.println("Admin bulundu: " + admin.getName());
        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())  // bu zaten hashlenmiş olmalı!
                .roles("ADMIN")  // veya .authorities(...) da kullanılabilir
                .build();
    }
}



