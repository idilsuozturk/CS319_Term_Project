package com.controllers;

import com.entities.Course;
import com.services.CourseService;

import java.util.List;

import org.springframework.web.bind.annotation.*;



@RestController

//@RequestMapping("/courses")
@RequestMapping("/api")

class CourseController {
    private final CourseService coursesService;
    
    public CourseController(CourseService coursesService) {
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
        return coursesService.createCourse( course.getCode(), course.getSection(), course.getInstructorID(), course.getTaIDs(), course.getSchedule());
    }

    @PutMapping("courses/{id}")
    public Course updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        //Course updatedCourse = coursesService.updateCourseById(id, course);
        /*if (updatedCourse != null) {
            return "Course updated successfully!";
        } else {
            return "Course not found!";
        }*/
        return coursesService.updateCourseByID(id, course);
    }
    @PostMapping("/delete")
    public void deleteCourse(@RequestParam Integer id) {
        coursesService.deleteCourseByID(id);
    }
}