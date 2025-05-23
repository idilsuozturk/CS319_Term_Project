package com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Student;


@Repository
public interface StudentRepository  extends JpaRepository<Student, Integer> {
    Optional<Student> findByStudentID(String studentID);
} 
