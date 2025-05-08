package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.entities.Course;


@Repository

public interface CoursesRepository  extends JpaRepository<Course, Integer> {
} 