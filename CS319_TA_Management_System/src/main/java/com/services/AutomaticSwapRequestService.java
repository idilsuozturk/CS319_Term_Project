package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.AutomaticSwapRequest;
import com.entities.ProctoringAssignment;
import com.entities.TA;
import com.repositories.AutomaticSwapRequestRepository;

@Service
public class AutomaticSwapRequestService {
    private final AutomaticSwapRequestRepository automaticSwapRequestRepository;

    private final NotificationService notificationService;

    private final ProctoringAssignmentService proctoringAssignmentService;

    private final TAService taService;

    public AutomaticSwapRequestService(AutomaticSwapRequestRepository automaticSwapRequestRepository, NotificationService notificationService, ProctoringAssignmentService proctoringAssignmentService, TAService taService) {
        this.automaticSwapRequestRepository = automaticSwapRequestRepository;
        this.notificationService = notificationService;
        this.proctoringAssignmentService = proctoringAssignmentService;
        this.taService = taService;
    }

    public List<AutomaticSwapRequest> getAllAutomaticSwapRequests() {
        return automaticSwapRequestRepository.findAll(); 
    }

    public AutomaticSwapRequest createAutomaticSwapRequest(int ownerID, String message, int firstTAID, int secondTAID, int firstTAsProctoringAssignmentID, int secondTAsProctoringAssignmentID) {
        return automaticSwapRequestRepository.save(new AutomaticSwapRequest(ownerID, message, firstTAID, secondTAID, firstTAsProctoringAssignmentID, secondTAsProctoringAssignmentID));
    }

    public void deleteAutomaticSwapRequestByID(Integer id) {
        automaticSwapRequestRepository.deleteById(id);  
    }

    public AutomaticSwapRequest updateAutomaticSwapRequestByID(Integer id, AutomaticSwapRequest automaticSwapRequest) {
        AutomaticSwapRequest existingAutomaticSwapRequest = automaticSwapRequestRepository.findById(id).orElse(null);  // Find AutomaticSwapRequest by ID
        if (existingAutomaticSwapRequest != null) {
            existingAutomaticSwapRequest.setRequestType(automaticSwapRequest.getRequestType());
            existingAutomaticSwapRequest.setMessage(automaticSwapRequest.getMessage());
            existingAutomaticSwapRequest.setOwnerID(automaticSwapRequest.getOwnerID());
            existingAutomaticSwapRequest.setPending(automaticSwapRequest.getPending());
            existingAutomaticSwapRequest.setFirstTAID(automaticSwapRequest.getFirstTAID());
            existingAutomaticSwapRequest.setSecondTAID(automaticSwapRequest.getSecondTAID());
            existingAutomaticSwapRequest.setFirstTAsProctoringAssignmentID(automaticSwapRequest.getFirstTAsProctoringAssignmentID());
            existingAutomaticSwapRequest.setSecondTAsProctoringAssignmentID(automaticSwapRequest.getSecondTAsProctoringAssignmentID());
            return automaticSwapRequestRepository.save(existingAutomaticSwapRequest); 
        }
        return null;  
    }

    public AutomaticSwapRequest getAutomaticSwapRequestByID(Integer id) {
        return automaticSwapRequestRepository.findById(id).orElse(null);  
    }

    public int initializeAutomaticSwapRequest(String date, int ownerID, String message, int firstTAsProctoringAssignmentID, int secondTAsProctoringAssignmentID){
        ProctoringAssignment firstProctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(firstTAsProctoringAssignmentID);
        ProctoringAssignment secondProctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(secondTAsProctoringAssignmentID);
        if (firstProctoringAssignment == null || secondProctoringAssignment == null){
            return 1;
        }
        int firstTAID = firstProctoringAssignment.getProctorID();
        int secondTAID = secondProctoringAssignment.getProctorID();
        if (!proctoringAssignmentService.isAvailable(firstTAID, secondTAsProctoringAssignmentID, firstTAsProctoringAssignmentID) || 
            !proctoringAssignmentService.isAvailable(secondTAID, firstTAsProctoringAssignmentID, secondTAsProctoringAssignmentID)){
            return 1;
        }
        TA firstTA = taService.getTAByID(firstTAID);
        TA secondTA = taService.getTAByID(secondTAID);
        if (firstTA.getLastAutomaticSwapTAID() == secondTAID && secondTA.getLastAutomaticSwapTAID() == firstTAID && 
            firstTA.getLastAutomaticSwapProctoringAssignmentID() == secondTAsProctoringAssignmentID && 
            secondTA.getLastAutomaticSwapProctoringAssignmentID() == firstTAsProctoringAssignmentID){
            int firstCount = firstTA.getSwapCount();
            firstCount++;
            if (firstCount == 3){
                return 2;
            }
        }
        else {
            firstTA.setSwapCount(1);
            secondTA.setSwapCount(1);
            firstTA.setLastAutomaticSwapTAID(secondTAID);
            secondTA.setLastAutomaticSwapTAID(firstTAID);
            firstTA.setLastAutomaticSwapProctoringAssignmentID(secondTAsProctoringAssignmentID);
            secondTA.setLastAutomaticSwapProctoringAssignmentID(firstTAsProctoringAssignmentID);
            taService.updateTAByID(firstTAID, firstTA);
            taService.updateTAByID(secondTAID, secondTA);
        }
        ArrayList<Integer> firstTAsProctoringAssignmentIDs = firstTA.getProctoringAssignmentIDs();
        ArrayList<Integer> secondTAsProctoringAssignmentIDs = secondTA.getProctoringAssignmentIDs();
        firstTAsProctoringAssignmentIDs.remove((Integer) firstTAsProctoringAssignmentID);
        secondTAsProctoringAssignmentIDs.remove((Integer) secondTAsProctoringAssignmentID);
        firstTAsProctoringAssignmentIDs.add(secondTAsProctoringAssignmentID);
        secondTAsProctoringAssignmentIDs.add(firstTAsProctoringAssignmentID);
        taService.updateTAByID(firstTAID, firstTA);
        taService.updateTAByID(secondTAID, secondTA);
        AutomaticSwapRequest automaticSwapRequest = createAutomaticSwapRequest(ownerID, message, firstTAID, secondTAID, firstTAsProctoringAssignmentID, secondTAsProctoringAssignmentID);
        notificationService.createNotification(date, automaticSwapRequest.getID(), 0);
        return 0;
    }
}
