 package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Admin;
import com.repositories.AdminRepository;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin createAdmin(Integer id, String email, String userName, String password, String tcNumber ) {
        Admin newAdmin = new TA(id, email, userName, password, tcNumber);
        return adminRepository.save(newAdmin);
    }

    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    public void deleteAdminById(Integer id) {
        adminRepository.deleteById(id);
    }

    public Admin updateAdmin(Integer id, Admin admin) {
        Admin existingAdmin = adminRepository.findById(id).orElse(null);
        if (existingAdmin != null) {
            adminRepository.deleteById(id);
            return adminRepository.save(admin);
        }
        return null;
    }
}