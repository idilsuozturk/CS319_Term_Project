package com.controllers;

import com.entities.DepartmentChair;
import com.services.DepartmentChairService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class DepartmentChairController {

    private final DepartmentChairService departmentChairService;
    
    public DepartmentChairController(DepartmentChairService departmentChairService) {
        this.departmentChairService = departmentChairService;
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
    public DepartmentChair createDepartmentChair(@RequestBody DepartmentChair departmentChair) {
        return departmentChairService.createDepartmentChair(
            departmentChair.getName(),
            departmentChair.getEmail(),
            departmentChair.getUsername(),
            departmentChair.getPassword(),
            departmentChair.getDepartmentCode()
        );
    }
 
    @PutMapping("/update-dept-chair/{id}")
    public DepartmentChair updateDepartmentChair(@PathVariable Integer id, @RequestBody DepartmentChair departmentChair) {
        return departmentChairService.updateDepartmentChair(id, departmentChair);
    }
    
    @DeleteMapping("/delete-dept-chair/{id}")
    public void deleteDepartmentChair(@PathVariable Integer id) {
        departmentChairService.deleteDepartmentChairById(id);
    }
}
