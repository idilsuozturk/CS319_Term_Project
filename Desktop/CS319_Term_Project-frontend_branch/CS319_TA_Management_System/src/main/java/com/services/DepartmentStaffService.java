 package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.DepartmentStaff;
import com.repositories.DepartmentStaffRepository;

import java.util.List;

@Service
public class DepartmentStaffService {

    private DepartmentStaffRepository departmentStaffRepository;

    public List<DepartmentStaff> getAllDepartmentStaff() {
        return departmentStaffRepository.findAll();
    }

    public DepartmentStaff createDepartmentStaff( String name, String email, String userName, String password, String departmentCode, String title) {
        DepartmentStaff newDepartmentStaff = new DepartmentStaff( name, email, userName, password, departmentCode, title);
        return departmentStaffRepository.save(newDepartmentStaff);
    }

    public DepartmentStaff getDepartmentStaffById(Integer id) {
        return departmentStaffRepository.findById(id).orElse(null);
    }

    public void deleteDepartmentStaffById(Integer id) {
        departmentStaffRepository.deleteById(id);
    }

    public DepartmentStaff updateDepartmentStaff(Integer id, DepartmentStaff deptStaff) {
        DepartmentStaff existingDepartmentStaff = departmentStaffRepository.findById(id).orElse(null);
        if (existingDepartmentStaff != null) {
            existingDepartmentStaff.setName(deptStaff.getName());
            existingDepartmentStaff.setEmail(deptStaff.getEmail());
            existingDepartmentStaff.setUsername(deptStaff.getUsername());
            existingDepartmentStaff.setPassword(deptStaff.getPassword());
            existingDepartmentStaff.setDepartmentCode(deptStaff.getDepartmentCode());
            existingDepartmentStaff.setTitle(deptStaff.getTitle());
            return departmentStaffRepository.save(deptStaff);
        }
        return null;
    }
}