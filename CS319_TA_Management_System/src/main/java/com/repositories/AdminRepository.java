package com.repositories;

import java.util.Optional;

import com.entities.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository  extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
} 
