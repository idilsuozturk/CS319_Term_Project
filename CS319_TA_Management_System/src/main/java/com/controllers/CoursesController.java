package com.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.entities.Schedule;
import com.entities.Course;
import com.services.CoursesService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController

@RequestMapping("/courses")


class CoursesController {
    private final CoursesService coursesService;
    
    //@Autowired
    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }
    
    @GetMapping("")
    public List <Course> listCourses() {
        return coursesService.getAllCourses();
    }

    @PostMapping("")
    public Course creatCourse(@RequestBody Course course) {
        return coursesService.createCourse(course.getId(), course.getCourseName(), course.getSection(), course.getInstructor(), course.getStudents(), course.getTAs(), course.getSchedule());
    }
    
}
