package com.controllers;

import com.entities.Instructor;
import com.services.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/test")
    public String test() {
        return "Instructor controller is up!";
    }

    @GetMapping("/list")
    public List<Instructor> listInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public Instructor getInstructor(@PathVariable Integer id) {
        return instructorService.getInstructorById(id);
    }

    @PostMapping("/create")
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return instructorService.createInstructor(
                instructor.getId(),
                instructor.getEmail(),
                instructor.getUserName(),
                instructor.getPassword(),
                instructor.getCourses(),
                instructor.getTas(),
                instructor.getDepartmentCode(),
                instructor.getTcNumber()
        );
    }

    @PutMapping("/update/{id}")
    public Instructor updateInstructor(@PathVariable Integer id, @RequestBody Instructor instructor) {
        return instructorService.updateInstructor(id, instructor);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteInstructor(@PathVariable Integer id) {
        instructorService.deleteInstructorById(id);
    }
}
