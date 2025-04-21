 package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.DepartmentStaff;
import com.repositories.DepartmentStaffRepository;

import java.util.List;

@Service
public class DepartmentStaffService {
    @Autowired
    private DepartmentStaffRepository departmentStaffRepository;

    public List<DepartmentStaff> getAllDepartmentStaff() {
        return departmentStaffRepository.findAll();
    }

    public DepartmentStaff createDepartmentStaff(  String email, String userName, String password, String departmentCode, String tcNumber) {
        DepartmentStaff newDepartmentStaff = new DepartmentStaff(  email, userName, password, departmentCode, tcNumber);
        return departmentStaffRepository.save(newDepartmentStaff);
    }

    public DepartmentStaff getDepartmentStaffById(Integer id) {
        return departmentStaffRepository.findById(id).orElse(null);
    }

    public void deleteDepartmentStaffById(Integer id) {
        departmentStaffRepository.deleteById(id);
    }

    public DepartmentStaff updateDepartmentStaff(Integer id, DepartmentStaff admin) {
        DepartmentStaff existingDepartmentStaff = departmentStaffRepository.findById(id).orElse(null);
        if (existingDepartmentStaff != null) {
            departmentStaffRepository.deleteById(id);
            return departmentStaffRepository.save(admin);
        }
        return null;
    }
}