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

import com.entities.Classroom;
import com.services.ClassroomService;



@RestController

//@RequestMapping("/Classrooms")
@RequestMapping("/api")

class ClassroomsController {
    private final ClassroomService classroomService;
    
    public ClassroomsController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping("/classrooms")
    
    public List <Classroom> listClassrooms() {
        return classroomService.getAllClassrooms();
    }

    @PostMapping("/create-Classroom")
    public Classroom createClassroom(@RequestBody Classroom classroom) {
         return classroomService.createClassroom(classroom.getClassroomName(), classroom.getCapacity(), classroom.getExamCapacity());
    }

    @GetMapping("/classroom/{id}")
    public Classroom getClassroom(@PathVariable Integer id) {
        return classroomService.getClassroomByID(id);
    }

    @PutMapping("update-Classroom/{id}")
    public Classroom updateClassroom(@PathVariable Integer id, @RequestBody Classroom Classroom) {
        return classroomService.updateClassroomByID(id, Classroom);
    }
    
    @DeleteMapping("/delete-Classroom/{id}")
    public String deleteClassroom(@PathVariable Integer id) {
        classroomService.deleteClassroomByID(id);
        return "Classroom deleted successfully!";
    }
}
