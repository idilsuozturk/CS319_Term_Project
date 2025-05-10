package com.controllers;

import com.entities.Instructor;
import com.entities.TaskSubmissionRequest;
import com.services.InstructorService;
import com.services.NotificationService;
import com.services.TaskSubmissionRequestService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InstructorController {

    private final InstructorService instructorService;

    private final NotificationService notificationService;

    private final TaskSubmissionRequestService taskSubmissionRequestService;

    public InstructorController(InstructorService instructorService, NotificationService notificationService, TaskSubmissionRequestService taskSubmissionRequestService) {
        this.instructorService = instructorService;
        this.notificationService = notificationService;
        this.taskSubmissionRequestService = taskSubmissionRequestService;
    }

    @GetMapping("/instructors")
    public List<Instructor> listInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/instructor/{id}")
    public Instructor getInstructor(@PathVariable Integer id) {
        return instructorService.getInstructorByID(id);
    }

    @PostMapping("/create-instructor")
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return instructorService.createInstructor(
            instructor.getName(),
            instructor.getEmail(),
            instructor.getUsername(),
            instructor.getPassword(),
            instructor.getDepartmentCode(),
            instructor.getCourseIDs(),
            instructor.getTaIDs()
        );
    }

    @PutMapping("/update-instructor/{id}")
    public Instructor updateInstructor(@PathVariable Integer id, @RequestBody Instructor instructor) {
        return instructorService.updateInstructorByID(id, instructor);
    }

    @DeleteMapping("/delete-instructor/{id}")
    public void deleteInstructor(@PathVariable Integer id) {
        instructorService.deleteInstructorByID(id);
    }

    @GetMapping("/{id}/viewnotifins")
    public List<String> viewNotifications(@PathVariable Integer id){
        return notificationService.viewNotificationsInstructor(id);
    }

    @GetMapping("/{id}/viewreq")
    public List<TaskSubmissionRequest> viewRequests(@PathVariable Integer id){
        return taskSubmissionRequestService.viewRequests(id);
    }

    @PostMapping("/{id}/apptaskreq")
    public boolean approveTaskSubmissionRequest(@PathVariable Integer id, @RequestParam Integer taskSubmissionID, @RequestParam String date){
        return taskSubmissionRequestService.approveTaskSubmissionRequest(date, id, taskSubmissionID);
    }

    @PostMapping("/{id}/rejtaskreq")
    public boolean rejectTaskSubmissionRequest(@PathVariable Integer id, @RequestParam Integer taskSubmissionID, @RequestParam String date){
        return taskSubmissionRequestService.rejectTaskSubmissionRequest(date, id, taskSubmissionID);
    }
}
