package com.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.entities.TA;
// import com.entities.Task;
import com.services.TAService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

        return taService.getTAById(id);
    }

    @PostMapping("/create")
    public TA createTA(@RequestBody TA ta) {
        return taService.createTA(
                
                ta.getEmail(),
                ta.getUserName(),
                ta.getPassword(),

                ta.getCurrentAssistingCourses(),
                ta.getCurrentTakingCourses(),
                ta.getAdvisor(),
                ta.getTotalWorkload(),

                ta.getTcNumber(),
                ta.getProctoringExams());
    }

    @PutMapping("/update/{id}")
    public TA updateTA(@PathVariable Integer id, @RequestBody TA ta) {
        return taService.updateTA(id, ta);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTA(@PathVariable Integer id) {
        taService.deleteTAById(id);
    }

    // Still gotta figure out the Schedule thing
    @GetMapping("/{id}/schedule")
    public String viewSchedule(@PathVariable Integer id) {
        taService.viewSchedule(id);
        return "Viewed Schedule for TA " + id;
    }

    // a stupid code for task submission, no task yyet

    // @PostMapping("/{id}/task-request")
    // public boolean submitTaskRequest(
    // @PathVariable Integer id,
    // @RequestParam Integer courseId,
    // @RequestBody Task task) {
    // return taService.submitTaskRequest(courseId, task);
    // }

    // Proctor task approve-reject ONLY for TA level. So the TA knows what got
    // rejected or approved by Superiors.
    @PostMapping("/{id}/proc-swap-request")
    public boolean sendProcSwapRequest(
            @PathVariable Integer id,
            @RequestParam Integer examId) {
        return taService.sendProcSwapRequest(id, examId);
    }

    @PostMapping("/{id}/approve-proc-swap")
    public boolean approveProcSwapRequest(
            @PathVariable Integer id,
            @RequestParam Integer examId) {
        return taService.approveProcSwapRequest(id, examId);
    }

    @PostMapping("/{id}/reject-proc-swap")
    public boolean rejectProcSwapRequest(
            @PathVariable Integer id,
            @RequestParam Integer examId) {
        return taService.rejectProcSwapRequest(id, examId);
    }

    @PostMapping("/{id}/request-leave")
    public boolean requestLeave(
            @PathVariable Integer id,
            @RequestParam String date,
            @RequestParam String reason) {
        return taService.requestLeave(id, date, reason);
    }

    @GetMapping("/{id}/workload")
    public boolean viewTotalWorkload(@PathVariable Integer id) {
        return taService.viewTotalWorkload(id);
    }

    @GetMapping("/{id}/proctoring")
    public boolean viewProctoringAssignment(@PathVariable Integer id) {
        return taService.viewProctoringAssignment(id);
    }

}
