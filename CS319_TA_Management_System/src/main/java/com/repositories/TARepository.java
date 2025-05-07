package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.TA;

// Literally a copy paste from CourseRepository,java

@Repository
public interface TARepository extends JpaRepository<TA, Integer> {
}
