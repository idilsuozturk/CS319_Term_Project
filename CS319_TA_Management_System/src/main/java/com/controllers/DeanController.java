package com.controllers;

import com.entities.Dean;
import com.services.DeanService;
import com.services.NotificationService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class DeanController {

    private final DeanService deanService;
    
    public DeanController(DeanService deanService, NotificationService notificationService) {
        this.deanService = deanService;
    }


    @GetMapping("/list-dean")
    public List<Dean> listDean() {
        return deanService.getAllDeans();
    }

    @GetMapping("/dean/{id}")
    public Dean getDean(@PathVariable Integer id) {
        return deanService.getDeanById(id);
    }

    @PostMapping("/create-dean")
    public Dean createDean(@RequestBody Dean chair) {
        return deanService.createDean(
            chair.getFirstName(),
            chair.getLastName(),
            chair.getEmail(),
            chair.getUsername(),
            chair.getPassword(),
            chair.getDepartmentCode()
        );
    }
 
    @PutMapping("/update-dean/{id}")
    public Dean updateDean(@PathVariable Integer id, @RequestBody Dean chair) {
        return deanService.updateDean(id, chair);
    }
    
    @DeleteMapping("/delete-dean/{id}")
    public void deleteDean(@PathVariable Integer id) {
        deanService.deleteDeanById(id);
    }
}
