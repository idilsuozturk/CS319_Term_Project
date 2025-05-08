package com.controllers;

import com.entities.DepartmentStaff;
import com.services.DepartmentStaffService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentStaffController {

    private final DepartmentStaffService departmentStaffService;
    
    public DepartmentStaffController(DepartmentStaffService departmentStaffService) {
        this.departmentStaffService = departmentStaffService;
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
                staff.getName(),
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
}
