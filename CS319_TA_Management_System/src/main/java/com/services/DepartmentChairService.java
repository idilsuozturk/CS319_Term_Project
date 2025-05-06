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

    public DepartmentChair createDepartmentChair(String name, String email, String userName, String password, String departmentCode,String title) {
        DepartmentChair newDepartmentChair = new DepartmentChair(name,email, userName, password, departmentCode, title);
        return departmentChairRepository.save(newDepartmentChair);
    }

    public DepartmentChair getDepartmentChairById(Integer id) {
        return departmentChairRepository.findById(id).orElse(null);
    }

    public void deleteDepartmentChairById(Integer id) {
        departmentChairRepository.deleteById(id);
    }

    public DepartmentChair updateDepartmentChair(Integer id, DepartmentChair deptChair) {
        DepartmentChair existingDepartmentChair = departmentChairRepository.findById(id).orElse(null);
        if (existingDepartmentChair != null) {
            existingDepartmentChair.setName(deptChair.getName());
            existingDepartmentChair.setEmail(deptChair.getEmail());
            existingDepartmentChair.setUsername(deptChair.getUsername());
            existingDepartmentChair.setPassword(deptChair.getPassword());
            existingDepartmentChair.setDepartmentCode(deptChair.getDepartmentCode());
            existingDepartmentChair.setTitle(deptChair.getTitle());
            
            return departmentChairRepository.save(deptChair);
        }
        return null;
    }
}
