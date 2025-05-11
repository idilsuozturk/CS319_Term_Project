package com.controllers;

import com.entities.Dean;
import com.entities.ProctoringAssignment;
import com.services.CoursesService;
import com.services.DeanService;
import com.services.NotificationService;
import com.services.ProctoringAssignmentService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeanController {

    private final DeanService deanService;

    private final ProctoringAssignmentService proctoringAssignmentService;

    private final CoursesService coursesService;
    
    public DeanController(DeanService deanService, ProctoringAssignmentService proctoringAssignmentService, CoursesService coursesService) {
        this.deanService = deanService;
        this.proctoringAssignmentService = proctoringAssignmentService;
        this.coursesService = coursesService;
    }


    @GetMapping("/list-dean")
    public List<Dean> listDean() {
        return deanService.getAllDeans();
    }

    @GetMapping("/dean/{id}")
    public Dean getDean(@PathVariable Integer id) {
        return deanService.getDeanById(id);
    }

    @PostMapping("/create-dean")
    public Dean createDean(@RequestBody Dean chair) {
        return deanService.createDean(
            chair.getFirstName(),
            chair.getLastName(),
            chair.getEmail(),
            chair.getUsername(),
            chair.getPassword(),
            chair.getDepartmentCode()
        );
    }
 
    @PutMapping("/update-dean/{id}")
    public Dean updateDean(@PathVariable Integer id, @RequestBody Dean chair) {
        return deanService.updateDean(id, chair);
    }
    
    @DeleteMapping("/delete-dean/{id}")
    public void deleteDean(@PathVariable Integer id) {
        deanService.deleteDeanById(id);
    }

    @PostMapping("/dean-exam-assign-")
    public String assignExamAndManualOrAutomatic(@RequestParam int year, @RequestParam int month, @RequestParam int day, @RequestParam int startTime, @RequestParam int endTime, @RequestParam int classroomID, @RequestParam int courseID, @RequestParam int proctorID, @RequestParam Integer id, @RequestParam String[] selectedDepartments, boolean manual, int restriction){
        if (manual){
            return proctoringAssignmentService.manualProctoringAssignmentByDean(new ProctoringAssignment(year, month, day, startTime, endTime, classroomID, courseID, proctorID), id, selectedDepartments);
        }
        else {
            int returned = proctoringAssignmentService.automaticProctoringAssignmentByDean(coursesService.getCourseByID(courseID).getCode(), proctorID, restriction, selectedDepartments);
            if (returned == 2){
                return "Successfull!";
            }
            else {
                return "Try again";
            }
        }
    }
    
}
