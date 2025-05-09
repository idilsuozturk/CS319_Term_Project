package com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.entities.Course;


@Repository

public interface CoursesRepository  extends JpaRepository<Course, Integer> {
    Optional<Course> findByCodeAndSection(String code, String section);
    Optional<Course> findByCode(String code);
} 