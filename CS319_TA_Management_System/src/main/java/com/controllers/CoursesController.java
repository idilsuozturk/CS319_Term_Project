package com.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.entities.Schedule;
import com.entities.Course;
import com.services.CoursesService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController

//@RequestMapping("/courses")
@RequestMapping("/api")

class CoursesController {
    private final CoursesService coursesService;
    
    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }
    
    //@GetMapping("/courselist")
    @GetMapping("/courses")
    
    public List <Course> listCourses() {
        return coursesService.getAllCourses();
    }

    @PostMapping("/create")
    public Course creatCourse(@RequestBody Course course) {
        return coursesService.createCourse( course.getCourseName(), course.getSection(), course.getInstructor(), course.getStudents(), course.getTAs());
    }

    @PutMapping("courses/{id}")
    public Course updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        //Course updatedCourse = coursesService.updateCourseById(id, course);
        /*if (updatedCourse != null) {
            return "Course updated successfully!";
        } else {
            return "Course not found!";
        }*/
        return coursesService.updateCourseById(id, course);
    }
    @PostMapping("/delete")
    public void deleteCourse(@RequestParam Integer id) {
        coursesService.deleteCourseById(id);
    }
}
