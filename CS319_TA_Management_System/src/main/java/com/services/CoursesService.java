package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Course; 
import com.repositories.CoursesRepository;

@Service
public class CoursesService {

    private  final CoursesRepository courseRepository;

    public CoursesService(CoursesRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();  
    }   

    public Course createCourse(String code, String section, Integer instructorID, ArrayList<Integer> taIDs, String[] schedule, boolean masterphd) {
        return courseRepository.save(new Course(code, section, instructorID, taIDs, schedule, masterphd));  
    }

 
    public void deleteCourseByID(Integer id) {
        courseRepository.deleteById(id);  
    }

    public Course updateCourseByID(Integer id, Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);  
        if (existingCourse != null) {
            existingCourse.setCode(course.getCode());
            existingCourse.setSection(course.getSection());
            existingCourse.setInstructorID(course.getInstructorID());
            existingCourse.setTaIDs(course.getTaIDs());
            existingCourse.setSchedule(course.getSchedule());
            existingCourse.setMasterphd(course.getMasterphd());
            return courseRepository.save(existingCourse);  
        }
        return null;  
    }

    public Course getCourseByID(Integer id) {
        return courseRepository.findById(id).orElse(null);  
    }

    public Course getCourseByCodeAndSection(String code, String section){
        return courseRepository.findByCodeAndSection(code, section).orElse(null);
    }

    public Course getCourseByCode(String code){
        return courseRepository.findByCode(code).orElse(null);
    }
}
