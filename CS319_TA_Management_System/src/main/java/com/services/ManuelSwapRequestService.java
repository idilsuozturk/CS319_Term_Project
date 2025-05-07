package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.ManuelSwapRequest;
import com.repositories.ManuelSwapRequestRepository;

@Service
public class ManuelSwapRequestService {
    private final ManuelSwapRequestRepository manuelSwapRequestRepository;

    public ManuelSwapRequestService(ManuelSwapRequestRepository manuelSwapRequestRepository) {
        this.manuelSwapRequestRepository = manuelSwapRequestRepository;
    }

    public List<ManuelSwapRequest> getAllManuelSwapRequests() {
        return manuelSwapRequestRepository.findAll(); 
    }

    public ManuelSwapRequest createManuelSwapRequest(String requestDate, int ownerID, int receiverID, int ownerCourseID, int receiverCourseID) {
        return manuelSwapRequestRepository.save(new ManuelSwapRequest(requestDate, ownerID, receiverID, ownerCourseID, receiverCourseID));  
    }

    public void deleteManuelSwapRequestByID(Integer id) {
        manuelSwapRequestRepository.deleteById(id);  
    }

    public ManuelSwapRequest updateManuelSwapRequestByID(Integer id, ManuelSwapRequest manuelSwapRequest) {
        ManuelSwapRequest existingManuelSwapRequest = manuelSwapRequestRepository.findById(id).orElse(null);  // Find ManuelSwapRequest by ID
        if (existingManuelSwapRequest != null) {
            existingManuelSwapRequest.setRequestDate(manuelSwapRequest.getRequestDate());
            existingManuelSwapRequest.setRequestType(manuelSwapRequest.getRequestType());
            existingManuelSwapRequest.setOwnerID(manuelSwapRequest.getOwnerID());
            existingManuelSwapRequest.setReceiverID(manuelSwapRequest.getReceiverID());
            existingManuelSwapRequest.setOwnerCourseID(manuelSwapRequest.getOwnerCourseID());
            existingManuelSwapRequest.setReceiverCourseID(manuelSwapRequest.getReceiverCourseID());
            return manuelSwapRequestRepository.save(existingManuelSwapRequest);  // Update ManuelSwapRequest in MySQL
        }
        return null;  // Return null if ManuelSwapRequest not found
    }

    public ManuelSwapRequest getManuelSwapRequestByID(Integer id) {
        return manuelSwapRequestRepository.findById(id).orElse(null);  // Find ManuelSwapRequest by ID
    }
}
