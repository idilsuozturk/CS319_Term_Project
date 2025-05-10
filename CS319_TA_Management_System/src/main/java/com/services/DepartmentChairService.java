package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.DepartmentChair;
import com.repositories.DepartmentChairRepository;

@Service
public class DepartmentChairService {
    private final DepartmentChairRepository departmentChairRepository;

    public DepartmentChairService(DepartmentChairRepository departmentChairRepository) {
        this.departmentChairRepository = departmentChairRepository;
    }

    public List<DepartmentChair> getAllDepartmentChair() {
        return departmentChairRepository.findAll();
    }

    public DepartmentChair createDepartmentChair(String firstName, String lastName, String email, String username, String password, String departmentCode) {
        DepartmentChair newDepartmentChair = new DepartmentChair(firstName, lastName, email, username, password, departmentCode);
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
            existingDepartmentChair.setFirstName(departmentChair.getFirstName());
            existingDepartmentChair.setLastName(departmentChair.getLastName());
            existingDepartmentChair.setEmail(departmentChair.getEmail());
            existingDepartmentChair.setUsername(departmentChair.getUsername());
            existingDepartmentChair.setPassword(departmentChair.getPassword());
            existingDepartmentChair.setDepartmentCode(departmentChair.getDepartmentCode());
            
            return departmentChairRepository.save(existingDepartmentChair);
        }
        return null;
    }

    /*private String convertLeaveofAbsenceRequestToString(LeaveofAbsenceRequest leaveofAbsenceRequest) {
        TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
        String output = "";
        if (ta != null){
            output = "\nSender TA Information:" + "\nTA name: " + ta.getName() + "\nTA ID: "
            + ta.getUsername() + "\nThe TA's Message:\n" + leaveofAbsenceRequest.getMessage() + "\nThe Dates TA wants to be on leave: " + 
            leaveofAbsenceRequest.getDates().get(0);
            for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                output += ", "+ leaveofAbsenceRequest.getDates().get(i);
            }
        }
        return output;
    }*/
}
