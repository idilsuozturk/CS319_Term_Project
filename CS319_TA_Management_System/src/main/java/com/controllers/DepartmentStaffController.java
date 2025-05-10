package com.controllers;

import com.entities.DepartmentStaff;
import com.services.AutomaticSwapRequestService;
import com.services.DepartmentStaffService;
import com.services.NotificationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentStaffController {
    private final AutomaticSwapRequestService automaticSwapRequestService;

    private final DepartmentStaffService departmentStaffService;

    private final NotificationService notificationService;
    
    public DepartmentStaffController(AutomaticSwapRequestService automaticSwapRequestService, DepartmentStaffService departmentStaffService, NotificationService notificationService) {
        this.automaticSwapRequestService = automaticSwapRequestService;
        this.departmentStaffService = departmentStaffService;
        this.notificationService = notificationService;
    }

    @GetMapping("/list-dept-staff")
    public List<DepartmentStaff> listDepartmentStaff() {
        return departmentStaffService.getAllDepartmentStaff();
    }

    @GetMapping("/dept-staff/{id}")
    public DepartmentStaff getDepartmentStaff(@PathVariable Integer id) {
        return departmentStaffService.getDepartmentStaffById(id);
    }

    @PostMapping("/create-dept-staff")
    public DepartmentStaff createDepartmentStaff(@RequestBody DepartmentStaff staff) {
        return departmentStaffService.createDepartmentStaff(
                staff.getFirstName(),
                staff.getLastName(),
                staff.getEmail(),
                staff.getUsername(),
                staff.getPassword(),
                staff.getDepartmentCode()
       );
    }

    @PutMapping("/update-dept-staff/{id}")
    public DepartmentStaff updateDepartmentStaff(@PathVariable Integer id, @RequestBody DepartmentStaff staff) {
        return departmentStaffService.updateDepartmentStaff(id, staff);
    }

    @DeleteMapping("/delete-dept-staff/{id}")
    public void deleteDepartmentStaff(@PathVariable Integer id) {
        departmentStaffService.deleteDepartmentStaffById(id);
    }

    @PostMapping("/{id}/initautoswapreq")
    public int initializeAutomaticSwapRequest(String date, int id, String message, int firstTAsProctoringAssignmentID, int secondTAsProctoringAssignmentID){
        return automaticSwapRequestService.initializeAutomaticSwapRequest(date, id, message, firstTAsProctoringAssignmentID, secondTAsProctoringAssignmentID);
    }
    
    @GetMapping("/{id}/viewnotifds")
    public List<String> viewNotifications(@PathVariable Integer id){
        return notificationService.viewNotificationsDepartmentStaff(id);
    }
}
