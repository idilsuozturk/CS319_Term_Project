package com.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Course;
import com.services.CoursesService;



@RestController

//@RequestMapping("/courses")
@RequestMapping("/api")

class CoursesController {
    private final CoursesService coursesService;
    
    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }


    @GetMapping("/courses")
    
    public List <Course> listCourses() {
        return coursesService.getAllCourses();
    }

    @PostMapping("/create-course")
    public Course createCourse(@RequestBody Course course) {
        if (coursesService.getCourseByCodeAndSection(course.getCode(), course.getSection()) != null) {
            return null;
        }
        return coursesService.createCourse( course.getCode(), course.getSection(), course.getInstructorID(), course.getTaIDs(), course.getSchedule(), course.getMasterphd());
    }

    @GetMapping("/course/{id}")
    public Course getCourse(@PathVariable Integer id) {
        return coursesService.getCourseByID(id);
    }

    @PutMapping("update-course/{id}")
    public Course updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        return coursesService.updateCourseByID(id, course);
    }
    
    @DeleteMapping("/delete-course/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        coursesService.deleteCourseByID(id);
        return "Course deleted successfully!";
    }
}
