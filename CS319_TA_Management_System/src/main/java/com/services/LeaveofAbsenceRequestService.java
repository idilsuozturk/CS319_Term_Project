package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.LeaveofAbsenceRequest;
import com.repositories.LeaveofAbsenceRequestRepository;

@Service
public class LeaveofAbsenceRequestService {
    private final LeaveofAbsenceRequestRepository leaveofAbsenceRequestRepository;

    public LeaveofAbsenceRequestService(LeaveofAbsenceRequestRepository leaveofAbsenceRequestRepository) {
        this.leaveofAbsenceRequestRepository = leaveofAbsenceRequestRepository;
    }

    public List<LeaveofAbsenceRequest> getAllLeaveofAbsenceRequests() {
        return leaveofAbsenceRequestRepository.findAll(); 
    }

    public LeaveofAbsenceRequest createLeaveofAbsenceRequest(int ownerID, String message, ArrayList<Integer> dates) {
        return leaveofAbsenceRequestRepository.save(new LeaveofAbsenceRequest(ownerID, message, dates));
    }

    public void deleteLeaveofAbsenceRequestByID(Integer id) {
        leaveofAbsenceRequestRepository.deleteById(id);  
    }

    public LeaveofAbsenceRequest updateLeaveofAbsenceRequestByID(Integer id, LeaveofAbsenceRequest leaveofAbsenceRequest) {
        LeaveofAbsenceRequest existingLeaveofAbsenceRequest = leaveofAbsenceRequestRepository.findById(id).orElse(null); 
        if (existingLeaveofAbsenceRequest != null){ 
            existingLeaveofAbsenceRequest.setRequestType(leaveofAbsenceRequest.getRequestType());
            existingLeaveofAbsenceRequest.setMessage(leaveofAbsenceRequest.getMessage());
            existingLeaveofAbsenceRequest.setOwnerID(leaveofAbsenceRequest.getOwnerID());
            existingLeaveofAbsenceRequest.setPending(leaveofAbsenceRequest.getPending());
            existingLeaveofAbsenceRequest.setDates(leaveofAbsenceRequest.getDates());
            return leaveofAbsenceRequestRepository.save(existingLeaveofAbsenceRequest);  
        }
        return null;  
    }

    public LeaveofAbsenceRequest getLeaveofAbsenceRequestByID(Integer id) {
        return leaveofAbsenceRequestRepository.findById(id).orElse(null); 
    }
}
