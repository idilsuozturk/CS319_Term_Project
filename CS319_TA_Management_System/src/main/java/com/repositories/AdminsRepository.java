package com.repositories;

import java.util.List;
import java.util.Optional;

import com.entities.Admin;

import io.micrometer.common.lang.NonNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface AdminsRepository  extends JpaRepository<Admin, Integer> {
    // List<Admin> findAll();
    // Optional<Admin> findById(Integer id);
    // Admin save(Admin admin);    
    // void  deleteById(Integer id);
    // void updateAdminById(Integer id, Admin admin);

    List<Admin> findAll(); // Retrieve all admins from the database

    Optional<Admin> findById(Integer id);

    
    Optional<Admin> findByUsername(String username);
    Admin save(@NonNull Admin admin); // Save a new admin or update an existing one

    void deleteById(Integer id); // Delete an admin by ID

    //unnecessary
    //void updateAdminById(@RequestParam(value = "id") Integer id, @RequestParam(value = "admin") Admin admin); // Update an admin by ID
    
} 
