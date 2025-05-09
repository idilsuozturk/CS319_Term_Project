package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Dean;

@Repository
public interface DeanRepository extends JpaRepository<Dean, Integer> {
}
