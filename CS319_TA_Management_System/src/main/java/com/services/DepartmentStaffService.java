package com.services;
import org.springframework.stereotype.Service;

import com.entities.DepartmentStaff;
import com.repositories.DepartmentStaffRepository;

import java.util.List;

@Service
public class DepartmentStaffService {

    private final DepartmentStaffRepository departmentStaffRepository;

    public DepartmentStaffService(DepartmentStaffRepository departmentStaffRepository) {
        this.departmentStaffRepository = departmentStaffRepository;
    }

    public List<DepartmentStaff> getAllDepartmentStaff() {
        return departmentStaffRepository.findAll();
    }

    public DepartmentStaff createDepartmentStaff(String firstName, String lastName, String email, String username, String password, String departmentCode) {
        DepartmentStaff newDepartmentStaff = new DepartmentStaff(firstName, lastName, email, username, password, departmentCode);
        return departmentStaffRepository.save(newDepartmentStaff);
    }

    public DepartmentStaff getDepartmentStaffById(Integer id) {
        return departmentStaffRepository.findById(id).orElse(null);
    }

    public void deleteDepartmentStaffById(Integer id) {
        departmentStaffRepository.deleteById(id);
    }

    public DepartmentStaff updateDepartmentStaff(Integer id, DepartmentStaff departmentStaff) {
        DepartmentStaff existingDepartmentStaff = departmentStaffRepository.findById(id).orElse(null);
        if (existingDepartmentStaff != null) {
            existingDepartmentStaff.setFirstName(departmentStaff.getFirstName());
            existingDepartmentStaff.setLastName(departmentStaff.getLastName());
            existingDepartmentStaff.setEmail(departmentStaff.getEmail());
            existingDepartmentStaff.setUsername(departmentStaff.getUsername());
            existingDepartmentStaff.setPassword(departmentStaff.getPassword());
            existingDepartmentStaff.setDepartmentCode(departmentStaff.getDepartmentCode());
            return departmentStaffRepository.save(existingDepartmentStaff);
        }
        return null;
    }
}
