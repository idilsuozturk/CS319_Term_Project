package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.DepartmentChair;
import com.repositories.DepartmentChairRepository;

@Service
public class DepartmentChairService {
    DepartmentChairRepository departmentChairRepository;

    public DepartmentChairService(DepartmentChairRepository departmentChairRepository) {
        this.departmentChairRepository = departmentChairRepository;
    }

    public List<DepartmentChair> getAllDepartmentChair() {
        return departmentChairRepository.findAll();
    }

    public DepartmentChair createDepartmentChair(String name, String email, String username, String password, String departmentCode) {
        DepartmentChair newDepartmentChair = new DepartmentChair(name,email, username, password, departmentCode);
        return departmentChairRepository.save(newDepartmentChair);
    }

    public DepartmentChair getDepartmentChairById(Integer id) {
        return departmentChairRepository.findById(id).orElse(null);
    }

    public void deleteDepartmentChairById(Integer id) {
        departmentChairRepository.deleteById(id);
    }

    public DepartmentChair updateDepartmentChair(Integer id, DepartmentChair departmentChair) {
        DepartmentChair existingDepartmentChair = departmentChairRepository.findById(id).orElse(null);
        if (existingDepartmentChair != null) {
            existingDepartmentChair.setName(departmentChair.getName());
            existingDepartmentChair.setEmail(departmentChair.getEmail());
            existingDepartmentChair.setUsername(departmentChair.getUsername());
            existingDepartmentChair.setPassword(departmentChair.getPassword());
            existingDepartmentChair.setDepartmentCode(departmentChair.getDepartmentCode());
            
            return departmentChairRepository.save(existingDepartmentChair);
        }
        return null;
    }
}
