package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.ManualSwapRequest;
import com.entities.ProctoringAssignment;
import com.entities.TA;
import com.repositories.ManualSwapRequestRepository;

@Service
public class ManualSwapRequestService {
    private final ManualSwapRequestRepository manualSwapRequestRepository;

    private final NotificationService notificationService;

    private final ProctoringAssignmentService proctoringAssignmentService;

    private final TAService taService;

    public ManualSwapRequestService(ManualSwapRequestRepository manualSwapRequestRepository, NotificationService notificationService, ProctoringAssignmentService proctoringAssignmentService, TAService taService) {
        this.manualSwapRequestRepository = manualSwapRequestRepository;
        this.notificationService = notificationService;
        this.proctoringAssignmentService = proctoringAssignmentService;
        this.taService = taService;
    }

    public List<ManualSwapRequest> getAllManualSwapRequests() {
        return manualSwapRequestRepository.findAll(); 
    }

    public ManualSwapRequest createManualSwapRequest(int ownerID, String message, int receiverID, int ownerProctoringAssignmentID, int receiverProctoringAssignmentID) {
        return manualSwapRequestRepository.save(new ManualSwapRequest(ownerID, message, receiverID, ownerProctoringAssignmentID, receiverProctoringAssignmentID));  
    }

    public void deleteManualSwapRequestByID(Integer id) {
        manualSwapRequestRepository.deleteById(id);  
    }

    public ManualSwapRequest updateManualSwapRequestByID(Integer id, ManualSwapRequest manualSwapRequest) {
        ManualSwapRequest existingManualSwapRequest = manualSwapRequestRepository.findById(id).orElse(null);  
        if (existingManualSwapRequest != null) {
            existingManualSwapRequest.setRequestType(manualSwapRequest.getRequestType());
            existingManualSwapRequest.setOwnerID(manualSwapRequest.getOwnerID());
            existingManualSwapRequest.setMessage(manualSwapRequest.getMessage());
            existingManualSwapRequest.setPending(manualSwapRequest.getPending());
            existingManualSwapRequest.setReceiverID(manualSwapRequest.getReceiverID());
            existingManualSwapRequest.setOwnerProctoringAssignmentID(manualSwapRequest.getOwnerProctoringAssignmentID());
            existingManualSwapRequest.setReceiverProctoringAssignmentID(manualSwapRequest.getReceiverProctoringAssignmentID());
            return manualSwapRequestRepository.save(existingManualSwapRequest); 
        }
        return null;  
    }

    public ManualSwapRequest getManualSwapRequestByID(Integer id) {
        return manualSwapRequestRepository.findById(id).orElse(null); 
    }

    public void initializeManualSwapRequest(String requestDate, String message, int requesterProctoringAssignmentID, int receiverProctoringAssignmentID){
        ProctoringAssignment firstTAProctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(requesterProctoringAssignmentID);
        ProctoringAssignment secondTAProctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(receiverProctoringAssignmentID);
        if (firstTAProctoringAssignment == null || secondTAProctoringAssignment == null) return;
        ManualSwapRequest newManualSwapRequest = createManualSwapRequest(firstTAProctoringAssignment.getProctorID(), message, secondTAProctoringAssignment.getProctorID(), requesterProctoringAssignmentID, receiverProctoringAssignmentID);
        notificationService.createNotification(requestDate, newManualSwapRequest.getID(), 0);
    }

    public List<ManualSwapRequest> viewRequests(int id){
        List<ManualSwapRequest> manualSwapRequests = getAllManualSwapRequests();
        ArrayList<ManualSwapRequest> output = new ArrayList<>();
        for (ManualSwapRequest manualSwapRequest : manualSwapRequests){
            if (manualSwapRequest.getReceiverID() == id && manualSwapRequest.getPending()) {
                output.add(manualSwapRequest);
            }
        }
        return output;
    }

    public boolean approveManualSwapRequest(String requestDate, Integer manualSwapRequestID) {
        ManualSwapRequest manualSwapRequest = getManualSwapRequestByID(manualSwapRequestID);
        if (manualSwapRequest != null) {
            TA owner = taService.getTAByID(manualSwapRequest.getOwnerID());
            TA receiver = taService.getTAByID(manualSwapRequest.getReceiverID());
            if (owner != null && receiver != null){
                if (manualSwapRequest.getReceiverProctoringAssignmentID() == -1){
                    ArrayList<Integer> ownerProctoringAssignments = owner.getProctoringAssignmentIDs();
                    ArrayList<Integer> receiverProctoringAssignments = receiver.getProctoringAssignmentIDs();
                    ownerProctoringAssignments.remove(Integer.valueOf(manualSwapRequest.getOwnerProctoringAssignmentID()));
                    receiverProctoringAssignments.add(manualSwapRequest.getOwnerProctoringAssignmentID());
                    owner.setProctoringAssignmentIDs(ownerProctoringAssignments);
                    receiver.setProctoringAssignmentIDs(receiverProctoringAssignments);
                    taService.updateTAByID(owner.getId(), owner);
                    taService.updateTAByID(receiver.getId(), receiver);
                }
                else {
                    ArrayList<Integer> ownerProctoringAssignments = owner.getProctoringAssignmentIDs();
                    ArrayList<Integer> receiverProctoringAssignments = receiver.getProctoringAssignmentIDs();
                    ownerProctoringAssignments.remove(Integer.valueOf(manualSwapRequest.getOwnerProctoringAssignmentID()));
                    receiverProctoringAssignments.remove(Integer.valueOf(manualSwapRequest.getReceiverProctoringAssignmentID()));
                    ownerProctoringAssignments.add(manualSwapRequest.getReceiverProctoringAssignmentID());
                    receiverProctoringAssignments.add(manualSwapRequest.getOwnerProctoringAssignmentID());
                    taService.updateTAByID(owner.getId(), owner);
                    taService.updateTAByID(receiver.getId(), receiver);
                }
                notificationService.createNotification(requestDate, manualSwapRequest.getID(), 1);
                manualSwapRequest.setPending(false);
                updateManualSwapRequestByID(manualSwapRequestID, manualSwapRequest);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean rejectManualSwapRequest(String requestDate, Integer manuelSwapRequestID) {
        ManualSwapRequest manualSwapRequest = getManualSwapRequestByID(manuelSwapRequestID);
        if (manualSwapRequest != null){
            notificationService.createNotification(requestDate, manualSwapRequest.getID(), 2);
            manualSwapRequest.setPending(false);
            updateManualSwapRequestByID(manuelSwapRequestID, manualSwapRequest);
        }
        return false;
    }
}
