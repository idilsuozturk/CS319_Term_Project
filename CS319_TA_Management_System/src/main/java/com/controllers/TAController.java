package com.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.entities.TA;
import com.services.TAService;

import java.util.ArrayList;
import java.util.List;
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

    private final TAService taService;

    public TAController(TAService taService) {
        this.taService = taService;
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
    public ArrayList<String> showPossibleTANames(@PathVariable int id, @RequestParam int proctoringAssignmentID){
        return taService.showPossibleTANames(id, proctoringAssignmentID);
    }

    @GetMapping("/{id}/showpossibleproctoringswaprequests")
    public ArrayList<String> showPossibleProctoringSwapRequests(@PathVariable int id, @RequestParam int receiverID, @RequestParam int proctoringAssignmentID){
        return taService.showPossibleProctoringSwapRequests(id, receiverID, proctoringAssignmentID);
    }

    @PostMapping("/{id}/initializemanuelswaprequest")
    public void initializeManuelSwapRequest(@RequestParam String requestDate, @RequestParam String message, @PathVariable int id, @RequestParam int receiverID, 
    @RequestParam int requesterProctoringAssignmentID, @RequestParam int receiverProctoringAssignmentID){
        taService.initializeManuelSwapRequest(requestDate, message, id, receiverID, requesterProctoringAssignmentID, receiverProctoringAssignmentID);
    }

    @GetMapping("/{id}/viewrequests")
    public ArrayList<String> viewRequests(@PathVariable int id){
        return taService.viewRequests(id);
    }

    @PostMapping("/approvemanuelswaprequests")
    public boolean approveManuelSwapRequest(@RequestParam String requestDate, @RequestParam int manuelSwapRequest){
        return taService.approveManuelSwapRequest(requestDate, manuelSwapRequest);
    }

    @PostMapping("/rejectmanuelswaprequests")
    public boolean rejectManuelSwapRequest(@RequestParam String requestDate, @RequestParam int manuelSwapRequest){
        return taService.rejectManuelSwapRequest(requestDate, manuelSwapRequest);
    }

    @PutMapping("/{taID}/addcourse")
    public boolean addCourse(@PathVariable Integer taID, @RequestParam Integer courseID, @RequestParam boolean taken){
        return taService.addCourse(courseID, taID, taken);
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
    public String[] viewProctoringAssignment(@PathVariable Integer id) {
        return taService.viewProctoringAssignments(id);
    }
}
