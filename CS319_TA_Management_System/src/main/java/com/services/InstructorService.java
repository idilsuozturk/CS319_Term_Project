 package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Instructor;
import com.repositories.InstructorRepository;

import java.util.List;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor createInstructor(String name,String email, String userName, String password, Integer[] courses, Integer[] tas, String departmentCode, String tcNumber) {
        Instructor newInstructor = new Instructor(name, email, userName, password, courses, tas, departmentCode, tcNumber);
        return instructorRepository.save(newInstructor);
    }

    public Instructor getInstructorById(Integer id) {
        return instructorRepository.findById(id).orElse(null);
    }

    public void deleteInstructorById(Integer id) {
        instructorRepository.deleteById(id);
    }

    public Instructor updateInstructor(Integer id, Instructor instructor) {
        Instructor existingInstructor = instructorRepository.findById(id).orElse(null);
        if (existingInstructor != null) {
           existingInstructor.setName(instructor.getName());
            existingInstructor.setEmail(instructor.getEmail());
            existingInstructor.setUsername(instructor.getUsername());
            existingInstructor.setPassword(instructor.getPassword());
            existingInstructor.setCourses(instructor.getCourses());
            existingInstructor.setTas(instructor.getTas());
            existingInstructor.setDepartmentCode(instructor.getDepartmentCode());
            existingInstructor.setTcNumber(instructor.getTcNumber());

            return instructorRepository.save(instructor);
        }
        return null;
    }
}