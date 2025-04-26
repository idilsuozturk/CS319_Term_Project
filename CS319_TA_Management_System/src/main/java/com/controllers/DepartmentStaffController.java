package com.controllers;

import com.entities.DepartmentStaff;
import com.services.DepartmentStaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deptstaff")
public class DepartmentStaffController {

    private final DepartmentStaffService departmentStaffService;
    
    public DepartmentStaffController(DepartmentStaffService departmentStaffService) {
        this.departmentStaffService = departmentStaffService;
    }

    @GetMapping("/test")
    public String test() {
        return "Department Staff controller is working!";
    }

    @GetMapping("/list")
    public List<DepartmentStaff> listDepartmentStaff() {
        return departmentStaffService.getAllDepartmentStaff();
    }

    @GetMapping("/{id}")
    public DepartmentStaff getDepartmentStaff(@PathVariable Integer id) {
        return departmentStaffService.getDepartmentStaffById(id);
    }

    @PostMapping("/create")
    public DepartmentStaff createDepartmentStaff(@RequestBody DepartmentStaff staff) {
        return departmentStaffService.createDepartmentStaff(
                staff.getName(),
                staff.getEmail(),
                staff.getUsername(),
                staff.getPassword(),
                staff.getDepartmentCode(),
                staff.getTcNumber()
        );
    }

    @PutMapping("/update/{id}")
    public DepartmentStaff updateDepartmentStaff(@PathVariable Integer id, @RequestBody DepartmentStaff staff) {
        return departmentStaffService.updateDepartmentStaff(id, staff);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDepartmentStaff(@PathVariable Integer id) {
        departmentStaffService.deleteDepartmentStaffById(id);
    }
}
