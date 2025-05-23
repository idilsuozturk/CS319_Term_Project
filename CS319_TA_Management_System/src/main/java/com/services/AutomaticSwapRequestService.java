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

    private final EmailService emailService;

    private final NotificationService notificationService;

    private final ProctoringAssignmentService proctoringAssignmentService;

    private final TAService taService;

    public AutomaticSwapRequestService(AutomaticSwapRequestRepository automaticSwapRequestRepository, EmailService emailService, NotificationService notificationService, ProctoringAssignmentService proctoringAssignmentService, TAService taService) {
        this.automaticSwapRequestRepository = automaticSwapRequestRepository;
        this.emailService = emailService;
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
        if (!proctoringAssignmentService.isAvailable(firstTAID, secondTAsProctoringAssignmentID, firstTAsProctoringAssignmentID, 0) || 
            !proctoringAssignmentService.isAvailable(secondTAID, firstTAsProctoringAssignmentID, secondTAsProctoringAssignmentID, 0)){
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
        String emailDateFirst = "";
        String emailDateSecond = "";
        if (firstProctoringAssignment.getDay() < 10) {
            emailDateFirst += "0" + firstProctoringAssignment.getDay();
        }
        else {
            emailDateFirst += firstProctoringAssignment.getDay();
        }
        if (firstProctoringAssignment.getMonth() < 10){
            emailDateFirst += "/0" + firstProctoringAssignment.getMonth();
        }
        else {
            emailDateFirst += "/" + firstProctoringAssignment.getMonth();
        }
        emailDateFirst += firstProctoringAssignment.getYear();
        if (secondProctoringAssignment.getDay() < 10) {
            emailDateSecond += "0" + secondProctoringAssignment.getDay();
        }
        else {
            emailDateSecond += secondProctoringAssignment.getDay();
        }
        if (secondProctoringAssignment.getMonth() < 10){
            emailDateSecond += "/0" + secondProctoringAssignment.getMonth();
        }
        else {
            emailDateSecond += "/" + secondProctoringAssignment.getMonth();
        }
        emailDateSecond += secondProctoringAssignment.getYear();
        emailService.sendEmail(firstTA.getEmail(), "Proctoring Assignment Swap", "Your Proctoring Assignment due to " + emailDateFirst + " has been swapped with TA " + secondTA.getName() + "'s Proctoring Assignment due to " + emailDateSecond + ". For more information, check your notifications page. Have a good day!");
        emailService.sendEmail(secondTA.getEmail(), "Proctoring Assignment Swap", "Your Proctoring Assignment due to " + emailDateSecond + " has been swapped with TA " + firstTA.getName() + "'s Proctoring Assignment due to " + emailDateFirst + ". For more information, check your notifications page. Have a good day!");
        return 0;
    }
}
