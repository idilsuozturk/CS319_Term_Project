package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.entities.Schedule;
import com.entities.Course; 
import com.repositories.CoursesRepository;

import java.util.List;

@Service
public class CoursesService {
    @Autowired
    private  CoursesRepository coursesRepository;

    public List<Course> getAllCourses() {
        return coursesRepository.findAll();  // Fetch all users
    }

    public Course createCourse( 
        Integer id,
        String courseName,
        Integer section,
        Integer instructor,
        Integer [] students,
        Integer [] TAs,
        Schedule schedule
    ) {
        return coursesRepository.save(new Course(id, courseName, section, instructor, students, TAs, schedule));  // Insert user into MySQL
    }

    /*public User findUserByEmail(String email) {
        return coursesRepository.findByEmail(email);  // Query user by email
    }*/
}
