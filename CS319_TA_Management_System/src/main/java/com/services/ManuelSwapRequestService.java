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

    public ManuelSwapRequest createManuelSwapRequest(int ownerID, String message, int receiverID, int ownerProctoringAssignmentID, int receiverProctoringAssignmentID) {
        return manuelSwapRequestRepository.save(new ManuelSwapRequest(ownerID, message, receiverID, ownerProctoringAssignmentID, receiverProctoringAssignmentID));  
    }

    public void deleteManuelSwapRequestByID(Integer id) {
        manuelSwapRequestRepository.deleteById(id);  
    }

    public ManuelSwapRequest updateManuelSwapRequestByID(Integer id, ManuelSwapRequest manuelSwapRequest) {
        ManuelSwapRequest existingManuelSwapRequest = manuelSwapRequestRepository.findById(id).orElse(null);  
        if (existingManuelSwapRequest != null) {
            existingManuelSwapRequest.setRequestType(manuelSwapRequest.getRequestType());
            existingManuelSwapRequest.setOwnerID(manuelSwapRequest.getOwnerID());
            existingManuelSwapRequest.setMessage(manuelSwapRequest.getMessage());
            existingManuelSwapRequest.setPending(manuelSwapRequest.getPending());
            existingManuelSwapRequest.setReceiverID(manuelSwapRequest.getReceiverID());
            existingManuelSwapRequest.setOwnerProctoringAssignmentID(manuelSwapRequest.getOwnerProctoringAssignmentID());
            existingManuelSwapRequest.setReceiverProctoringAssignmentID(manuelSwapRequest.getReceiverProctoringAssignmentID());
            return manuelSwapRequestRepository.save(existingManuelSwapRequest); 
        }
        return null;  
    }

    public ManuelSwapRequest getManuelSwapRequestByID(Integer id) {
        return manuelSwapRequestRepository.findById(id).orElse(null); 
    }
}
