package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Dean;
import com.repositories.DeanRepository;

@Service
public class DeanService {
    DeanRepository deanRepository;

    public DeanService(DeanRepository deanRepository) {
        this.deanRepository = deanRepository;
    }

    public List<Dean> getAllDeans() {
        return deanRepository.findAll();
    }


    public Dean createDean(String name, String email, String username, String password, String departmentCode) {
        Dean newDean = new Dean(name, email, username, password, departmentCode);
        return deanRepository.save(newDean);
    }
    
    public Dean getDeanById(Integer id) {
        return deanRepository.findById(id).orElse(null);
    }

    public void deleteDeanById(Integer id) {
        deanRepository.deleteById(id);
    }

    public Dean updateDean(Integer id, Dean dean) {
        Dean existingDean = deanRepository.findById(id).orElse(null);
        if (existingDean != null) {
            existingDean.setName(dean.getName());
            existingDean.setEmail(dean.getEmail());
            existingDean.setUsername(dean.getUsername());
            existingDean.setPassword(dean.getPassword());
            existingDean.setDepartmentCode(dean.getDepartmentCode());
            return deanRepository.save(dean);
        }
        return null;
    }
}
