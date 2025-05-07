package com.services;

import org.springframework.stereotype.Service;

import com.entities.Course; 
import com.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private  final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();  
    }   

    public Course createCourse(String code, Integer section, Integer instructorID, ArrayList<Integer> taIDs, String[] schedule) {
        return courseRepository.save(new Course(code, section, instructorID, taIDs, schedule));  // Insert user into MySQL
    }

 
    public void deleteCourseByID(Integer id) {
        courseRepository.deleteById(id);  
    }

    public Course updateCourseByID(Integer id, Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);  // Find user by ID
        if (existingCourse != null) {
            existingCourse.setCode(course.getCode());
            existingCourse.setSection(course.getSection());
            existingCourse.setInstructorID(course.getInstructorID());
            existingCourse.setTaIDs(course.getTaIDs());
            existingCourse.setSchedule(course.getSchedule());
            return courseRepository.save(existingCourse);  // Update user in MySQL
        }
        return null;  // Return null if user not found
    }

    public Course getCourseByID(Integer id) {
        return courseRepository.findById(id).orElse(null);  // Find user by ID
    }
}