package com.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.entities.Course;
import com.entities.ManualSwapRequest;
import com.entities.ProctoringAssignment;
import com.entities.TA;
import com.entities.TaskTypes;
import com.services.CoursesService;
import com.services.LeaveofAbsenceRequestService;
import com.services.ManualSwapRequestService;
import com.services.NotificationService;
import com.services.ProctoringAssignmentService;
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

    private final ManualSwapRequestService manualSwapRequestService;

    private final NotificationService notificationService;

    private final ProctoringAssignmentService proctoringAssignmentService;

    private final TAService taService;

    private final TaskSubmissionRequestService taskSubmissionRequestService;


    public TAController(TAService taService, CoursesService coursesService, LeaveofAbsenceRequestService leaveofAbsenceRequestService, ManualSwapRequestService manualSwapRequestService,
                        TaskSubmissionRequestService taskSubmissionRequestService, NotificationService notificationService, ProctoringAssignmentService proctoringAssignmentService) {
        this.taService = taService;
        this.coursesService = coursesService;
        this.leaveofAbsenceRequestService = leaveofAbsenceRequestService;
        this.manualSwapRequestService = manualSwapRequestService;
        this.taskSubmissionRequestService = taskSubmissionRequestService;
        this.notificationService = notificationService;
        this.proctoringAssignmentService = proctoringAssignmentService;
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
                ta.getFirstName(),
                ta.getLastName(),
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
    public List<TA> showPossibleTANames(@PathVariable int id){
        return taService.showTAs(id);
    }

    @GetMapping("/{id}/showpossibleprocswapreq")
    public List<ProctoringAssignment> showPossibleProctoringAssignments(@RequestParam int receiverID, @RequestParam int proctoringAssignmentID){
        return proctoringAssignmentService.showPossibleProctoringAssignments(receiverID, proctoringAssignmentID);
    }

    @PostMapping("/initmanreq")
    public void initializeManualSwapRequest(@RequestParam String requestDate, @RequestParam String message, @RequestParam int requesterProctoringAssignmentID, @RequestParam int receiverProctoringAssignmentID){
        manualSwapRequestService.initializeManualSwapRequest(requestDate, message, requesterProctoringAssignmentID, receiverProctoringAssignmentID);
    }

    @GetMapping("/{id}/viewreq")
    public List<ManualSwapRequest> viewRequests(@PathVariable int id){
        return manualSwapRequestService.viewRequests(id);
    }

    @PostMapping("/appmanreq")
    public boolean approveManualSwapRequest(@RequestParam String requestDate, @RequestParam int manuelSwapRequest){
        return manualSwapRequestService.approveManualSwapRequest(requestDate, manuelSwapRequest);
    }

    @PostMapping("/rejmanreq")
    public boolean rejectManualSwapRequest(@RequestParam String requestDate, @RequestParam int manuelSwapRequest){
        return manualSwapRequestService.rejectManualSwapRequest(requestDate, manuelSwapRequest);
    }

    @PutMapping("/{taID}/addcourse")
    public boolean addCourse(@PathVariable Integer taID, @RequestParam String code, @RequestParam String section){
        Course course;
        boolean taken;
        if (!section.equals("assisted")){
            course = coursesService.getCourseByCodeAndSection(code, section);
            taken = true;
            if (course == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course name is incorrect!");
            }
        }
        else {
            course = coursesService.getCourseByCode(code);
            taken = false;
            if (course == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course name or course section is incorrect!");
            }
        }
        return coursesService.addCourse(course.getId(), taID, taken);
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
        return proctoringAssignmentService.viewProctoringAssignments(id);
    }

    @GetMapping("/{id}/viewnotifta")
    public List<String> viewNotifications(@PathVariable Integer id){
        return notificationService.viewNotificationsTA(id);
    }

    @GetMapping("/{id}/viewcouta")
    public List<String> viewCourses(@PathVariable Integer id, @RequestParam boolean option){
        if (option){
            return coursesService.viewCoursesTaken(id);
        }
        else {
            return coursesService.viewCoursesAssisted(id);
        }
    }

    @PostMapping("/{id}/createloareq")
    public boolean createLeaveofAbsenceRequest(@PathVariable Integer id, @RequestParam String requestDate, @RequestParam String message, @RequestParam ArrayList<String> dates){
        return leaveofAbsenceRequestService.createLeaveofAbsenceRequest(id, null, null, null);
    }

    @PostMapping("/{id}/createtaskreq")
    public void createTaskSubmissionRequest(@PathVariable Integer id, @RequestParam TaskTypes taskType, @RequestParam String taskDate, @RequestParam String requestDate, @RequestParam String message, @RequestParam int courseID){
        taskSubmissionRequestService.createTaskSubmissionRequest(id, taskType, taskDate, requestDate, message, courseID);
    }

    @GetMapping("/{id}/viewhist")
    public List<String> viewTaskHistory(@PathVariable Integer id){
        return taskSubmissionRequestService.viewTaskHistory(id);
    }
}
