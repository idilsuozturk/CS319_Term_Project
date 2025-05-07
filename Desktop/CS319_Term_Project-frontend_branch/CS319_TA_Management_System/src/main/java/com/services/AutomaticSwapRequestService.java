package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.AutomaticSwapRequest;
import com.repositories.AutomaticSwapRequestRepository;

@Service
public class AutomaticSwapRequestService {
    private final AutomaticSwapRequestRepository automaticSwapRequestRepository;

    public AutomaticSwapRequestService(AutomaticSwapRequestRepository automaticSwapRequestRepository) {
        this.automaticSwapRequestRepository = automaticSwapRequestRepository;
    }

    public List<AutomaticSwapRequest> getAllAutomaticSwapRequests() {
        return automaticSwapRequestRepository.findAll(); 
    }

    public AutomaticSwapRequest createAutomaticSwapRequest(String requestDate, int ownerID, int firstTAID, int secondTAID) {
        return automaticSwapRequestRepository.save(new AutomaticSwapRequest(requestDate, ownerID, firstTAID, secondTAID));  
    }

    public void deleteAutomaticSwapRequestByID(Integer id) {
        automaticSwapRequestRepository.deleteById(id);  
    }

    public AutomaticSwapRequest updateAutomaticSwapRequestByID(Integer id, AutomaticSwapRequest automaticSwapRequest) {
        AutomaticSwapRequest existingAutomaticSwapRequest = automaticSwapRequestRepository.findById(id).orElse(null);  // Find AutomaticSwapRequest by ID
        if (existingAutomaticSwapRequest != null) {
            existingAutomaticSwapRequest.setRequestDate(automaticSwapRequest.getRequestDate());
            existingAutomaticSwapRequest.setRequestType(automaticSwapRequest.getRequestType());
            existingAutomaticSwapRequest.setOwnerID(automaticSwapRequest.getOwnerID());
            existingAutomaticSwapRequest.setFirstTAID(automaticSwapRequest.getFirstTAID());
            existingAutomaticSwapRequest.setSecondTAID(automaticSwapRequest.getSecondTAID());
            return automaticSwapRequestRepository.save(existingAutomaticSwapRequest);  // Update AutomaticSwapRequest in MySQL
        }
        return null;  // Return null if AutomaticSwapRequest not found
    }

    public AutomaticSwapRequest getAutomaticSwapRequestByID(Integer id) {
        return automaticSwapRequestRepository.findById(id).orElse(null);  // Find AutomaticSwapRequest by ID
    }
}
