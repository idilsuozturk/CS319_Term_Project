package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Student;
import com.repositories.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student createStudent(String firstName, String lastName, String studentID){
        return studentRepository.save(new Student(firstName, lastName, studentID));
    }

    public void deleteStudentByID(Integer id) {
        studentRepository.deleteById(id);  
    }

    public Student updateStudentByID(Integer id, Student student) {
        Student existingStudent = studentRepository.findById(id).orElse(null);  
        if (existingStudent != null) {
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setStudentID(student.getStudentID());
            return studentRepository.save(existingStudent);  
        }
        return null;  
    }

    public Student getStudentByID(Integer id) {
        return studentRepository.findById(id).orElse(null);  
    }

    public Student getStudentByStudentID(String studentID) {
        return studentRepository.findByStudentID(studentID).orElse(null);
    }
}
