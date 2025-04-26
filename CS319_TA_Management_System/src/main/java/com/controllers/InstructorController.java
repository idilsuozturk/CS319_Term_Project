package com.controllers;

import com.entities.Instructor;
import com.services.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/instructors")
    public List<Instructor> listInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/instructor/{id}")
    public Instructor getInstructor(@PathVariable Integer id) {
        return instructorService.getInstructorById(id);
    }

    @PostMapping("/create-instructor")
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return instructorService.createInstructor(
                instructor.getName(),
                instructor.getEmail(),
                instructor.getUsername(),
                instructor.getPassword(),
                instructor.getCourses(),
                instructor.getTas(),
                instructor.getDepartmentCode(),
                instructor.getTcNumber()
        );
    }

    @PutMapping("/update-instructor/{id}")
    public Instructor updateInstructor(@PathVariable Integer id, @RequestBody Instructor instructor) {
        return instructorService.updateInstructor(id, instructor);
    }

    @DeleteMapping("/delete-instructor/{id}")
    public void deleteInstructor(@PathVariable Integer id) {
        instructorService.deleteInstructorById(id);
    }
}
