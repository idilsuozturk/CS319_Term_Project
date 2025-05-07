package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
}