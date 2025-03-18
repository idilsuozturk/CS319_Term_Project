package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.Course;

import io.micrometer.common.lang.NonNull;

@Repository

public interface CoursesRepository  extends JpaRepository<Course, Integer> {
    List<Course> findAll();
    
    @SuppressWarnings("null")
    @Override
    Optional<Course> findById(@SuppressWarnings("null") @RequestParam(value = "id") Integer id);

    @SuppressWarnings("null")
    Course save(@NonNull Course course);    
} 