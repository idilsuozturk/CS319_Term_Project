package com.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Course;
import com.entities.Instructor;
import com.entities.TaskSubmissionRequest;
import com.services.CoursesService;
import com.services.InstructorService;
import com.services.NotificationService;
import com.services.TaskSubmissionRequestService;

@RestController
@RequestMapping("/api")
public class InstructorController {
    private final InstructorService instructorService;

    private final CoursesService coursesService;

    private final NotificationService notificationService;

    private final TaskSubmissionRequestService taskSubmissionRequestService;

    public InstructorController(InstructorService instructorService, CoursesService coursesService, NotificationService notificationService, TaskSubmissionRequestService taskSubmissionRequestService) {
        this.instructorService = instructorService;
        this.coursesService = coursesService;
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
            instructor.getFirstName(),
            instructor.getLastName(),
            instructor.getEmail(),
            instructor.getUsername(),
            instructor.getPassword(),
            instructor.getDepartmentCode(),
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

    @GetMapping("/{id}/viewreqins")
    public List<TaskSubmissionRequest> viewRequests(@PathVariable Integer id){
        return taskSubmissionRequestService.viewRequests(id);
    }

    @GetMapping("/{id}/viewcourins")
    public List<Course> viewCourses(@PathVariable Integer id){
        return coursesService.viewCoursesGiven(id);
    }

    @GetMapping("/{id}/viewassisttas")
    public List<String> viewAssistingTAs(@PathVariable Integer id){
        return coursesService.viewAssistingTAs(id);
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
