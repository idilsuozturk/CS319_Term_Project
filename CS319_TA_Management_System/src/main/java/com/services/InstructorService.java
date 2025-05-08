package com.services;

import org.springframework.stereotype.Service;

import com.entities.Instructor;
import com.repositories.InstructorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorService {
    private InstructorRepository instructorRepository;

    

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor createInstructor(String name, String username, String email, String password, String departmentCode, ArrayList<Integer> courseIDs, ArrayList<Integer> taIDs) {
        Instructor newInstructor = new Instructor(name, email, username, password, departmentCode, courseIDs, taIDs);
        return instructorRepository.save(newInstructor);
    }

    public Instructor getInstructorByID(Integer id) {
        return instructorRepository.findById(id).orElse(null);
    }

    public void deleteInstructorByID(Integer id) {
        instructorRepository.deleteById(id);
    }

    public Instructor updateInstructorByID(Integer id, Instructor instructor) {
        Instructor existingInstructor = instructorRepository.findById(id).orElse(null);
        if (existingInstructor != null) {
            existingInstructor.setUsername(instructor.getUsername());
            existingInstructor.setEmail(instructor.getEmail());
            existingInstructor.setPassword(instructor.getPassword());
            existingInstructor.setCourseIDs(instructor.getCourseIDs());
            existingInstructor.setDepartmentCode(instructor.getDepartmentCode());
            existingInstructor.setTaIDs(instructor.getTaIDs());
            return instructorRepository.save(existingInstructor);
        }
        return null;
    }
}
