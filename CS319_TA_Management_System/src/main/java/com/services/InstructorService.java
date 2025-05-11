package com.services;

import org.springframework.stereotype.Service;

import com.entities.Instructor;
import com.repositories.InstructorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor createInstructor(String firstName, String lastName, String username, String email, String password, String departmentCode, ArrayList<Integer> taIDs) {
        Instructor newInstructor = new Instructor(firstName, lastName, email, username, password, departmentCode, taIDs);
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
            existingInstructor.setFirstName(instructor.getFirstName());
            existingInstructor.setLastName(instructor.getLastName());
            existingInstructor.setUsername(instructor.getUsername());
            existingInstructor.setEmail(instructor.getEmail());
            existingInstructor.setPassword(instructor.getPassword());
            existingInstructor.setDepartmentCode(instructor.getDepartmentCode());
            existingInstructor.setTaIDs(instructor.getTaIDs());
            return instructorRepository.save(existingInstructor);
        }
        return null;
    }
    /*private String convertTaskSubmissionRequestToString(TaskSubmissionRequest taskSubmissionRequest) {
        TA ta = taService.getTAByID(taskSubmissionRequest.getOwnerID());
        String output = "";
        if (ta != null){
            output = "Task Information:\nTask Type: ";
            if (taskSubmissionRequest.getTaskType() == TaskTypes.GRADING){
                output += "Grading\nCourse Code: " + coursesService.getCourseByID(taskSubmissionRequest.getCourseID()).getCode() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
            } 
            else if (taskSubmissionRequest.getTaskType() == TaskTypes.LAB) {
                output += "Lab\nLab Duration: " + taskSubmissionRequest.getDuration() + "\nCourse Code: " + coursesService.getCourseByID(taskSubmissionRequest.getCourseID()).getCode() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
            }
            else if (taskSubmissionRequest.getTaskType() == TaskTypes.OFFICE_HOUR){
                output += "Office Hour\nCourse Code: " + coursesService.getCourseByID(taskSubmissionRequest.getCourseID()).getCode() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
            }
            else if (taskSubmissionRequest.getTaskType() == TaskTypes.PROCTORING){
                output += "Proctoring\nCourse Code: " + coursesService.getCourseByID(taskSubmissionRequest.getCourseID()).getCode() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
            }
            else {
                output += "Recitation\nCourse Code: " + coursesService.getCourseByID(taskSubmissionRequest.getCourseID()).getCode() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
            }
            output += "\nSender TA Information:" + "\nTA name: " + ta.getName() + "\nTA ID: "
            + ta.getUsername() + "\nThe TA's Message:\n" + taskSubmissionRequest.getMessage();
        }
        return output;
    }*/
}
