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

import com.entities.Student;
import com.services.StudentService;



@RestController

@RequestMapping("/api")

class StudentsController {
    private final StudentService studentService;
    
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @GetMapping("/students")
    public List <Student> listStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/create-student")
    public Student createStudent(@RequestBody Student student) {
        if (student == null){
            return null;
        }
        if (studentService.getStudentByStudentID(student.getStudentID()) != null) {
            return null;
        }
        return studentService.createStudent( student.getFirstName(), student.getLastName(), student.getStudentID());
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentService.getStudentByID(id);
    }

    @PutMapping("update-student/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        return studentService.updateStudentByID(id, student);
    }
    
    @DeleteMapping("/delete-Student/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudentByID(id);
        return "Student deleted successfully!";
    }
}
