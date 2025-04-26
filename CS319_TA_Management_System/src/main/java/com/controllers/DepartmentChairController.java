package com.controllers;

import com.entities.DepartmentChair;
import com.entities.DepartmentStaff;
import com.repositories.DepartmentChairRepository;
import com.services.DepartmentChairService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    public DepartmentChair createDepartmentChair(@RequestBody DepartmentChair chair) {
        return departmentChairService.createDepartmentChair(
                chair.getName(),
                chair.getEmail(),
                chair.getUsername(),
                chair.getPassword(),
                chair.getDepartmentCode(),
                chair.getTitle(),
                chair.getTcNumber()
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
}
