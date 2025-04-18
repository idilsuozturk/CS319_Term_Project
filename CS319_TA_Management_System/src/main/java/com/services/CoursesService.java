package com.services;

import org.springframework.stereotype.Service;


import com.entities.Schedule;
import com.entities.Course; 
import com.repositories.CoursesRepository;

import java.util.List;

@Service
public class CoursesService {

    private  final CoursesRepository coursesRepository;

    public CoursesService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }
    
    public List<Course> getAllCourses() {
        return coursesRepository.findAll();  // Fetch all users
    }   

    public Course createCourse( 
        String courseName,
        Integer section,
        Integer instructor,
        Integer [] students,
        Integer [] TAs
        //Schedule schedule
    ) {
        return coursesRepository.save(new Course( courseName, section, instructor, students, TAs));  // Insert user into MySQL
    }

 
    public void deleteCourseById(Integer id) {
        coursesRepository.deleteById(id);  
    }

    public Course updateCourseById(Integer id, Course course) {
        Course existingCourse = coursesRepository.findById(id).orElse(null);  // Find user by ID
        if (existingCourse != null) {
            existingCourse.setCourseName(course.getCourseName());
            existingCourse.setSection(course.getSection());
            existingCourse.setInstructor(course.getInstructor());
            existingCourse.setStudents(course.getStudents());
            existingCourse.setTAs(course.getTAs());
            //existingCourse.setSchedule(course.getSchedule());
            return coursesRepository.save(existingCourse);  // Update user in MySQL
        }
        return null;  // Return null if user not found
    }

    public Course getCourseById(Integer id) {
        return coursesRepository.findById(id).orElse(null);  // Find user by ID
    }

}
