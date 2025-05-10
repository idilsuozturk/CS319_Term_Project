package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.ProctoringAssignment;
import com.entities.TA;
import com.repositories.ProctoringAssignmentRepository;
@Service
public class ProctoringAssignmentService {
    private final ProctoringAssignmentRepository proctoringAssignmentRepository;

    private final TAService taService;

    public ProctoringAssignmentService(ProctoringAssignmentRepository proctoringAssignmentRepository, TAService taService) {
        this.proctoringAssignmentRepository = proctoringAssignmentRepository;
        this.taService = taService;
    }
    
    public List<ProctoringAssignment> getAllProctoringAssignments() {
        return proctoringAssignmentRepository.findAll();  
    }   

    public ProctoringAssignment createProctoringAssignment(int year, int month, int day, int startTime, int endTime, int classroomID, int courseID, int proctorID) {
        return proctoringAssignmentRepository.save(new ProctoringAssignment(year, month, day, startTime, endTime, classroomID, courseID, proctorID));  
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
            existingProctoringAssignment.setClassroomID(proctoringAssignment.getClassroomID());
            existingProctoringAssignment.setCourseID(proctoringAssignment.getCourseID());
            existingProctoringAssignment.setProctorID(proctoringAssignment.getProctorID());
            return proctoringAssignmentRepository.save(existingProctoringAssignment);  
        }
        return null;  
    }

    public ProctoringAssignment getProctoringAssignmentByID(Integer id) {
        return proctoringAssignmentRepository.findById(id).orElse(null); 
    }

    public List<ProctoringAssignment> showPossibleProctoringAssignments(Integer receiverID, Integer proctoringAssignmentID) {
        ProctoringAssignment proctoringAssignment = getProctoringAssignmentByID(proctoringAssignmentID);
        TA requester = taService.getTAByID(proctoringAssignment.getProctorID());
        TA receiver = taService.getTAByID(receiverID);
        if (proctoringAssignment == null || requester == null || receiver == null){
            return null;
        }
        ArrayList<ProctoringAssignment> output = new ArrayList<ProctoringAssignment>();
        if (isAvailable(receiverID, proctoringAssignmentID, -1)) output.add(null);
        for (int id : receiver.getProctoringAssignmentIDs()){
            if (isAvailable(requester.getId(), id, proctoringAssignmentID)){
                ProctoringAssignment newProctoringAssignment = getProctoringAssignmentByID(id);
                output.add(newProctoringAssignment);
            }
        }
        return output;
    }

    public List<ProctoringAssignment> viewProctoringAssignments(Integer id) {
        TA ta = taService.getTAByID(id);
        if (ta != null){
            ArrayList<ProctoringAssignment> output = new ArrayList<ProctoringAssignment>();
            for (int i : ta.getProctoringAssignmentIDs()){
                ProctoringAssignment proctoringAssignment = getProctoringAssignmentByID(i);
                output.add(proctoringAssignment);
            }
            return output;
        }
        return null;
    }

    public boolean isAvailable(int taID, Integer proctoringAssignmentID, Integer ignore){
        ProctoringAssignment proctoringAssignment = getProctoringAssignmentByID(proctoringAssignmentID);
        ProctoringAssignment proctoringAssignmentToBeIgnored = getProctoringAssignmentByID(ignore);
        TA ta = taService.getTAByID(taID);
        if (proctoringAssignment == null || ta == null){
            return false;
        }
        int day = proctoringAssignment.getDay();
        int month = proctoringAssignment.getMonth();
        int year = proctoringAssignment.getYear();
        for (String j : ta.getOnLeaveDates()){
            if (Integer.valueOf(j.substring(0,2)) == day &&
                Integer.valueOf(j.substring(3, 5)) == month && 
                Integer.valueOf(j.substring(6)) == year) return false;
        }
        int start = proctoringAssignment.getStartTime();
        int end = proctoringAssignment.getEndTime();
        int indexStart = start / 100;
        int indexEnd = end / 100;
        if (start % 100 <= 30){
            indexStart -= 9;
        }
        else {
            indexStart -= 8;
        }
        if (end % 100 <= 30){
            indexEnd -= 9;
        }
        else {
            indexEnd -= 8;
        }
        for (int i = indexStart; i <= indexEnd;i++){
            if (ta.getSchedule()[(day - 1) * 14 + i] == null){
                return false;
            }   
        }
        for (int id : ta.getProctoringAssignmentIDs()){
            if (proctoringAssignmentToBeIgnored != null && id == proctoringAssignmentToBeIgnored.getID()) continue;
            ProctoringAssignment test = getProctoringAssignmentByID(id);
            if (test != null) {
                int testDay = test.getDay();
                if (day == testDay){
                    int testMonth = test.getMonth();
                    if (month == testMonth){
                        int testYear = test.getYear();
                        if (year == testYear){
                            int testStart = test.getStartTime();
                            int testEnd = test.getEndTime();
                            if (testStart > start && end > testStart || start > testStart && testEnd > start) return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
