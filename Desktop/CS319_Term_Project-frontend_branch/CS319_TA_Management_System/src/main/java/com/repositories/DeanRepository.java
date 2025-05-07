package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Dean;

@Repository
public interface DeanRepository extends JpaRepository<Dean, Integer> {
    List<Dean> findAll();
    Optional<Dean> findById(Integer id);
    Dean save(Dean dean);
    void deleteById(Integer id);


    
}
