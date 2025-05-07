package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Roles;
import com.entities.User;
import com.repositories.UserRepository;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();  // Fetch all users
    }

    public User createUser(String name, String email, String userName, String password) {
        return userRepository.save(new User(name,email, userName, password, Roles.UNKNOWN));  // Insert user into MySQL
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);  
    }

    public User updateUserById(Integer id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);  // Find user by ID
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);  // Update user in MySQL
        }
        return null;  // Return null if user not found
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);  // Find user by ID
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);  // Find user by email
    }
}
