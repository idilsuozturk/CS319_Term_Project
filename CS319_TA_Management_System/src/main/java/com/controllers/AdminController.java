package com.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Admin;
import com.services.AdminsService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController

@RequestMapping("/api") 

public class AdminController {
    
    private final AdminsService adminsService;

    public AdminController(AdminsService adminsService) {
        this.adminsService = adminsService;
    }

    @GetMapping("/admins")
    public List<Admin> listAdmins() {
        return adminsService.getAllAdmins(); // Fetch all admins
    }

    @PostMapping("/create-admin")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminsService.createAdmin(admin.getUsername(), admin.getName(), admin.getEmail(), admin.getPassword()); // Create a new admin
    }

    @PostMapping("/delete-admin")
    public void deleteAdmin(@RequestBody Integer id) {
        adminsService.deleteAdminById(id); // Delete admin by ID
    }

    @PutMapping("update-admin/{id}")
    public Admin updateAdmin(@RequestBody Admin admin) {
        return adminsService.updateAdminById(admin.getId(), admin); // Update admin by ID
    }

    @PostMapping("/admin/{id}")
    public Admin getAdmin(@RequestBody Integer id) {
        return adminsService.getAdminById(id); // Get admin by ID
    }
}
