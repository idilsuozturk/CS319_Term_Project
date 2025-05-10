package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Admin;
import com.repositories.AdminsRepository;

@Service
public class AdminsService {
    private final AdminsRepository adminRepository;

    public AdminsService(AdminsRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();  // Fetch all admins
    }

    public Admin findAdminByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);  // Find admin by username
    }

    public Admin createAdmin(String username, String firstName, String lastName, String email, String password) {
        Admin newAdmin = new Admin(username, firstName, lastName, email, password);  // Create new admin object
        return adminRepository.save(newAdmin);  // Insert admin into MySQL
    }

    public void deleteAdminById(Integer id) {
        adminRepository.deleteById(id);  
    }

    public Admin updateAdminById(Integer id, Admin admin) {
        Admin existingAdmin = adminRepository.findById(id).orElse(null);  // Find admin by ID
        if (existingAdmin != null) {
            existingAdmin.setFirstName(admin.getFirstName());
            existingAdmin.setLastName(admin.getLastName());
            existingAdmin.setEmail(admin.getEmail());
            existingAdmin.setPassword(admin.getPassword());
            return adminRepository.save(existingAdmin);  // Update admin in MySQL
        }
        return null;  // Return null if admin not found
    }

    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id).orElse(null);  // Find admin by ID
    }
}
