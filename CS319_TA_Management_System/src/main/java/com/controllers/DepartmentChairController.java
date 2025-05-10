package com.controllers;

import com.entities.DepartmentChair;
import com.entities.LeaveofAbsenceRequest;
import com.services.DepartmentChairService;
import com.services.LeaveofAbsenceRequestService;
import com.services.NotificationService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class DepartmentChairController {

    private final DepartmentChairService departmentChairService;

    private final LeaveofAbsenceRequestService leaveofAbsenceRequestService;

    private final NotificationService notificationService;
    
    public DepartmentChairController(DepartmentChairService departmentChairService, LeaveofAbsenceRequestService leaveofAbsenceRequestService, NotificationService notificationService) {
        this.departmentChairService = departmentChairService;
        this.leaveofAbsenceRequestService = leaveofAbsenceRequestService;
        this.notificationService = notificationService;
    }


    @GetMapping("/list-dept-chair")
    public List<DepartmentChair> listDepartmentChair() {
        return departmentChairService.getAllDepartmentChair();
    }

    @GetMapping("/dept-chair/{id}")
    public DepartmentChair getDepartmentChair(@PathVariable Integer id) {
        return departmentChairService.getDepartmentChairById(id);
    }

    @PostMapping("/create-dept-chair")
    public DepartmentChair createDepartmentChair(@RequestBody DepartmentChair chair) {
        return departmentChairService.createDepartmentChair(
            chair.getName(),
            chair.getEmail(),
            chair.getUsername(),
            chair.getPassword(),
            chair.getDepartmentCode()
        );
    }
 
    @PutMapping("/update-dept-chair/{id}")
    public DepartmentChair updateDepartmentChair(@PathVariable Integer id, @RequestBody DepartmentChair chair) {
        return departmentChairService.updateDepartmentChair(id, chair);
    }
    
    @DeleteMapping("/delete-dept-chair/{id}")
    public void deleteDepartmentChair(@PathVariable Integer id) {
        departmentChairService.deleteDepartmentChairById(id);
    }

    @GetMapping("/viewreq")
    public List<LeaveofAbsenceRequest> viewRequests(){
        return leaveofAbsenceRequestService.viewRequests();
    }

    @PostMapping("/{id}/apploa")
    public boolean approveLeaveofAbsenceRequest(@PathVariable Integer id, @RequestParam String requestDate, @RequestParam int leaveofAbsenceID){
        return leaveofAbsenceRequestService.approveLeaveofAbsenceRequest(requestDate, id, leaveofAbsenceID);
    }

    @PostMapping("/{id}/rejloa")
    public boolean rejectLeaveofAbsenceRequest(@PathVariable Integer id, @RequestParam String requestDate, @RequestParam int leaveofAbsenceID){
        return leaveofAbsenceRequestService.approveLeaveofAbsenceRequest(requestDate, id, leaveofAbsenceID);
    }

    @GetMapping("/{id}/viewnotifdc")
    public List<String> viewNotifications(@PathVariable Integer id){
        return notificationService.viewNotificationsDepartmentChair(id);
    }
}
