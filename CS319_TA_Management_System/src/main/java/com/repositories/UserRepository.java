package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.User;

import io.micrometer.common.lang.NonNull;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    List<User> findAll();
    Optional<User> findById(Integer id);
    User save(@NonNull User user);
    void deleteById(Integer id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
