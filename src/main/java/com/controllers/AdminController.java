package com.controllers;

import com.entities.Admin;
import com.services.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/test")
    public String test() {
        return "Admin controller is working!";
    }

    @GetMapping("/list")
    public List<Admin> listAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public Admin getAdmin(@PathVariable Integer id) {
        return adminService.getAdminById(id);
    }

    @PostMapping("/create")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(
                admin.getTcNumber(),
                admin.getName(),
                admin.getSurname(),
                admin.getEmail(),
                admin.getPassword()
        );
    }

    @PutMapping("/update/{id}")
    public Admin updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdminById(id);
    }
}