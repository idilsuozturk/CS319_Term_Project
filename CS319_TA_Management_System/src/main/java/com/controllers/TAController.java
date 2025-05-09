package com.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.entities.Course;
import com.entities.ManuelSwapRequest;
import com.entities.ProctoringAssignment;
import com.entities.TA;
import com.entities.TaskTypes;
import com.services.CoursesService;
import com.services.LeaveofAbsenceRequestService;
import com.services.ManuelSwapRequestService;
import com.services.NotificationService;
import com.services.TAService;
import com.services.TaskSubmissionRequestService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tas")
public class TAController {

    private final CoursesService coursesService;

    private final LeaveofAbsenceRequestService leaveofAbsenceRequestService;

    private final ManuelSwapRequestService manuelSwapRequestService;

    private final NotificationService notificationService;

    private final TAService taService;

    private final TaskSubmissionRequestService taskSubmissionRequestService;


    public TAController(TAService taService, CoursesService coursesService, LeaveofAbsenceRequestService leaveofAbsenceRequestService, ManuelSwapRequestService manuelSwapRequestService,
                        TaskSubmissionRequestService taskSubmissionRequestService, NotificationService notificationService) {
        this.taService = taService;
        this.coursesService = coursesService;
        this.leaveofAbsenceRequestService = leaveofAbsenceRequestService;
        this.manuelSwapRequestService = manuelSwapRequestService;
        this.taskSubmissionRequestService = taskSubmissionRequestService;
        this.notificationService = notificationService;
    }

    @GetMapping("/test")

    public String test() {
        return "TA controller pls work";
    }

    @GetMapping("/list")
    public List<TA> listTAs() {
        return taService.getAllTAs();
    }

    @GetMapping("/{id}")
    public TA getTA(@PathVariable Integer id) {

        return taService.getTAByID(id);
    }

    @PostMapping("/create")
    public TA createTA(@RequestBody TA ta) {
        return taService.createTA(
                ta.getName(),
                ta.getEmail(),
                ta.getUsername(),
                ta.getPassword(),
                ta.getMaster(),
                ta.getAdvisorID());
    }

    @PutMapping("/update/{id}")
    public TA updateTA(@PathVariable Integer id, @RequestBody TA ta) {
        return taService.updateTAByID(id, ta);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTA(@PathVariable Integer id) {
        taService.deleteTAByID(id);
    }

    @GetMapping("/{id}/showpossibletanames")
    public List<TA> showPossibleTANames(@PathVariable int id, @RequestParam int proctoringAssignmentID){
        return taService.showPossibleTANames(id, proctoringAssignmentID);
    }

    @GetMapping("/{id}/showpossibleprocswapreq")
    public List<ProctoringAssignment> showPossibleProctoringSwapRequests(@PathVariable int id, @RequestParam int receiverID, @RequestParam int proctoringAssignmentID){
        return taService.showPossibleProctoringSwapRequests(id, receiverID, proctoringAssignmentID);
    }

    @PostMapping("/{id}/initemanreq")
    public void initializeManuelSwapRequest(@RequestParam String requestDate, @RequestParam String message, @PathVariable int id, @RequestParam int receiverID, 
                                            @RequestParam int requesterProctoringAssignmentID, @RequestParam int receiverProctoringAssignmentID){
        manuelSwapRequestService.initializeManuelSwapRequest(requestDate, message, id, receiverID, requesterProctoringAssignmentID, receiverProctoringAssignmentID);
    }

    @GetMapping("/{id}/viewreq")
    public List<ManuelSwapRequest> viewRequests(@PathVariable int id){
        return manuelSwapRequestService.viewRequests(id);
    }

    @PostMapping("/appmanreq")
    public boolean approveManuelSwapRequest(@RequestParam String requestDate, @RequestParam int manuelSwapRequest){
        return manuelSwapRequestService.approveManuelSwapRequest(requestDate, manuelSwapRequest);
    }

    @PostMapping("/rejmanreq")
    public boolean rejectManuelSwapRequest(@RequestParam String requestDate, @RequestParam int manuelSwapRequest){
        return manuelSwapRequestService.rejectManuelSwapRequest(requestDate, manuelSwapRequest);
    }

    @PutMapping("/{taID}/addcourse")
    public boolean addCourse(@PathVariable Integer taID, @RequestParam String code, @RequestParam String section, @RequestParam boolean taken){
        Course course;
        if (taken){
            course = coursesService.getCourseByCodeAndSection(code, section);
        }
        else {
            course = coursesService.getCourseByCode(code);
        }
        if (course == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        return taService.addCourse(course.getId(), taID, taken);
    }

    @GetMapping("/{id}/schedule")
    public String[] viewSchedule(@PathVariable Integer id, @RequestParam int day) {
        return taService.viewSchedule(id, day);
    }

    @PutMapping("/{id}/mode")
    public void changeMode(@PathVariable Integer id, @RequestParam byte mode){
        taService.changeMode(id, mode);
    }

    @GetMapping("/{id}/workload")
    public int viewTotalWorkload(@PathVariable Integer id) {
        return taService.viewTotalWorkload(id);
    }

    @GetMapping("/{id}/proctoring")
    public List<ProctoringAssignment> viewProctoringAssignment(@PathVariable Integer id) {
        return taService.viewProctoringAssignments(id);
    }

    @GetMapping("/{id}/viewnot")
    public List<String> viewNotifications(@PathVariable Integer id){
        return notificationService.viewNotificationsTA(id);
    }

    @PostMapping("/{id}/createloareq")
    public void createLeaveofAbsenceRequest(@PathVariable Integer id, @RequestParam String requestDate, @RequestParam String message, @RequestParam ArrayList<String> dates){
        leaveofAbsenceRequestService.createLeaveofAbsenceRequest(id, null, null, null);
    }

    @PostMapping("/{id}/createtaskreq")
    public void createTaskSubmissionRequest(@PathVariable Integer id, @RequestParam TaskTypes taskType, @RequestParam String taskDate, @RequestParam String requestDate, @RequestParam String message, @RequestParam int courseID){
        taskSubmissionRequestService.createTaskSubmissionRequest(id, taskType, taskDate, requestDate, message, courseID);
    }
}
