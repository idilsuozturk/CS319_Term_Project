package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.ProctoringAssignment;
import com.repositories.ProctoringAssignmentRepository;
@Service
public class ProctoringAssignmentService {
    private  final ProctoringAssignmentRepository proctoringAssignmentRepository;

    public ProctoringAssignmentService(ProctoringAssignmentRepository proctoringAssignmentRepository) {
        this.proctoringAssignmentRepository = proctoringAssignmentRepository;
    }
    
    public List<ProctoringAssignment> getAllProctoringAssignments() {
        return proctoringAssignmentRepository.findAll();  
    }   

    public ProctoringAssignment createProctoringAssignment(int year, int month, int day, int startTime, int endTime, String examPlace, int courseID, int proctorID, String[] students) {
        return proctoringAssignmentRepository.save(new ProctoringAssignment(year, month, day, startTime, endTime, examPlace, courseID, proctorID, students));  
    }

 
    public void deleteProctoringAssignmentByID(Integer id) {
        proctoringAssignmentRepository.deleteById(id);  
    }

    public ProctoringAssignment updateProctoringAssignmentByID(Integer id, ProctoringAssignment proctoringAssignment) {
        ProctoringAssignment existingProctoringAssignment = proctoringAssignmentRepository.findById(id).orElse(null);  
        if (existingProctoringAssignment != null) {
            existingProctoringAssignment.setYear(proctoringAssignment.getYear());
            existingProctoringAssignment.setMonth(proctoringAssignment.getMonth());
            existingProctoringAssignment.setDay(proctoringAssignment.getDay());
            existingProctoringAssignment.setStartTime(proctoringAssignment.getStartTime());
            existingProctoringAssignment.setEndTime(proctoringAssignment.getEndTime());
            existingProctoringAssignment.setCourseID(proctoringAssignment.getCourseID());
            existingProctoringAssignment.setProctorID(proctoringAssignment.getProctorID());
            return proctoringAssignmentRepository.save(existingProctoringAssignment);  
        }
        return null;  
    }

    public ProctoringAssignment getProctoringAssignmentByID(Integer id) {
        return proctoringAssignmentRepository.findById(id).orElse(null); 
    }
}
