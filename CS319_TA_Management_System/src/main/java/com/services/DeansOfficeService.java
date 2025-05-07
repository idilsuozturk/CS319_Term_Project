package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.DeansOffice;
import com.repositories.DeansOfficeRepository;

@Service
public class DeansOfficeService {
    DeansOfficeRepository deansOfficeRepository;

    public DeansOfficeService(DeansOfficeRepository deansOfficeRepository) {
        this.deansOfficeRepository = deansOfficeRepository;
    }

    public List<DeansOffice> getAllDeansOffice() {
        return deansOfficeRepository.findAll();
    }


    public DeansOffice createDeansOffice(String name, String email, String username, String password, String departmentCode) {
        DeansOffice newDeansOffice = new DeansOffice(name, email, username, password, departmentCode);
        return deansOfficeRepository.save(newDeansOffice);
    }
    
    public DeansOffice getDeansOfficeById(Integer id) {
        return deansOfficeRepository.findById(id).orElse(null);
    }

    public void deleteDeansOfficeById(Integer id) {
        deansOfficeRepository.deleteById(id);
    }

    public DeansOffice updateDeansOffice(Integer id, DeansOffice dean) {
        DeansOffice existingDeansOffice = deansOfficeRepository.findById(id).orElse(null);
        if (existingDeansOffice != null) {
            existingDeansOffice.setName(dean.getName());
            existingDeansOffice.setEmail(dean.getEmail());
            existingDeansOffice.setUsername(dean.getUsername());
            existingDeansOffice.setPassword(dean.getPassword());
            existingDeansOffice.setDepartmentCode(dean.getDepartmentCode());
            return deansOfficeRepository.save(existingDeansOffice);
        }
        return null;
    }
}
