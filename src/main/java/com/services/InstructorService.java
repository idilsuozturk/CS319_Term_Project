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

    public Instructor createInstructor(Integer id, String email, String userName, String password, Integer[] courses, Integer[] tas, String departmentCode, String tcNumber) {
        Instructor newInstructor = new Instructor(id, email, userName, password, courses, tas, departmentCode, tcNumber);
        return instructorRepository.save(newInstructor);
    }

    public Instructor getInstructorById(Integer id) {
        return instructorRepository.findById(id).orElse(null);
    }

    public void deleteInstructorById(Integer id) {
        instructorRepository.deleteById(id);
    }

    public Instructor updateInstructor(Integer id, Instructor admin) {
        Instructor existingInstructor = instructorRepository.findById(id).orElse(null);
        if (existingInstructor != null) {
            instructorRepository.deleteById(id);
            return instructorRepository.save(admin);
        }
        return null;
    }
}