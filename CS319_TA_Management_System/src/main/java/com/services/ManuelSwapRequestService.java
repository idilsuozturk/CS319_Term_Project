package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.ManuelSwapRequest;
import com.entities.TA;
import com.repositories.ManuelSwapRequestRepository;

@Service
public class ManuelSwapRequestService {
    private final ManuelSwapRequestRepository manuelSwapRequestRepository;

    private final NotificationService notificationService;

    private final TAService taService;

    public ManuelSwapRequestService(ManuelSwapRequestRepository manuelSwapRequestRepository, NotificationService notificationService, TAService taService) {
        this.manuelSwapRequestRepository = manuelSwapRequestRepository;
        this.notificationService = notificationService;
        this.taService = taService;
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

    public void initializeManuelSwapRequest(String requestDate, String message, int requesterID, int receiverID, int requesterProctoringAssignmentID, int receiverProctoringAssignmentID){
        ManuelSwapRequest newManuelSwapRequest = createManuelSwapRequest(requesterID, message, receiverID, requesterProctoringAssignmentID, receiverProctoringAssignmentID);
        notificationService.createNotification(requestDate, newManuelSwapRequest.getID(), 0);
    }

    public List<ManuelSwapRequest> viewRequests(int id){
        List<ManuelSwapRequest> manuelSwapRequests = getAllManuelSwapRequests();
        ArrayList<ManuelSwapRequest> output = new ArrayList<>();
        for (ManuelSwapRequest manuelSwapRequest : manuelSwapRequests){
            if (manuelSwapRequest.getReceiverID() == id && manuelSwapRequest.getPending()) {
                output.add(manuelSwapRequest);
            }
        }
        return output;
    }

    public boolean approveManuelSwapRequest(String requestDate, Integer manuelSwapRequestID) {
        ManuelSwapRequest manuelSwapRequest = getManuelSwapRequestByID(manuelSwapRequestID);
        if (manuelSwapRequest != null) {
            TA owner = taService.getTAByID(manuelSwapRequest.getOwnerID());
            TA receiver = taService.getTAByID(manuelSwapRequest.getReceiverID());
            if (owner != null && receiver != null){
                if (manuelSwapRequest.getReceiverProctoringAssignmentID() == -1){
                    ArrayList<Integer> ownerProctoringAssignments = owner.getProctoringAssignmentIDs();
                    ArrayList<Integer> receiverProctoringAssignments = receiver.getProctoringAssignmentIDs();
                    ownerProctoringAssignments.remove(Integer.valueOf(manuelSwapRequest.getOwnerProctoringAssignmentID()));
                    receiverProctoringAssignments.add(manuelSwapRequest.getOwnerProctoringAssignmentID());
                    owner.setProctoringAssignmentIDs(ownerProctoringAssignments);
                    receiver.setProctoringAssignmentIDs(receiverProctoringAssignments);
                    taService.updateTAByID(owner.getId(), owner);
                    taService.updateTAByID(receiver.getId(), receiver);
                }
                else {
                    ArrayList<Integer> ownerProctoringAssignments = owner.getProctoringAssignmentIDs();
                    ArrayList<Integer> receiverProctoringAssignments = receiver.getProctoringAssignmentIDs();
                    ownerProctoringAssignments.remove(Integer.valueOf(manuelSwapRequest.getOwnerProctoringAssignmentID()));
                    receiverProctoringAssignments.remove(Integer.valueOf(manuelSwapRequest.getReceiverProctoringAssignmentID()));
                    ownerProctoringAssignments.add(manuelSwapRequest.getReceiverProctoringAssignmentID());
                    receiverProctoringAssignments.add(manuelSwapRequest.getOwnerProctoringAssignmentID());
                    taService.updateTAByID(owner.getId(), owner);
                    taService.updateTAByID(receiver.getId(), receiver);
                }
                notificationService.createNotification(requestDate, manuelSwapRequest.getID(), 1);
                manuelSwapRequest.setPending(false);
                updateManuelSwapRequestByID(manuelSwapRequestID, manuelSwapRequest);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean rejectManuelSwapRequest(String requestDate, Integer manuelSwapRequestID) {
        ManuelSwapRequest manuelSwapRequest = getManuelSwapRequestByID(manuelSwapRequestID);
        if (manuelSwapRequest != null){
            notificationService.createNotification(requestDate, manuelSwapRequest.getID(), 2);
            manuelSwapRequest.setPending(false);
            updateManuelSwapRequestByID(manuelSwapRequestID, manuelSwapRequest);
        }
        return false;
    }
}
