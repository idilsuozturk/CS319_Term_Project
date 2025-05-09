package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.LeaveofAbsenceRequest;
import com.entities.TA;
import com.repositories.LeaveofAbsenceRequestRepository;

@Service
public class LeaveofAbsenceRequestService {
    private final LeaveofAbsenceRequestRepository leaveofAbsenceRequestRepository;

    private final NotificationService notificationService;

    private final TAService taService;

    public LeaveofAbsenceRequestService(LeaveofAbsenceRequestRepository leaveofAbsenceRequestRepository, NotificationService notificationService, TAService taService) {
        this.leaveofAbsenceRequestRepository = leaveofAbsenceRequestRepository;
        this.notificationService = notificationService;
        this.taService = taService;
    }

    public List<LeaveofAbsenceRequest> getAllLeaveofAbsenceRequests() {
        return leaveofAbsenceRequestRepository.findAll(); 
    }

    public LeaveofAbsenceRequest createLeaveofAbsenceRequest(int ownerID, String message, ArrayList<String> dates) {
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
            existingLeaveofAbsenceRequest.setRespondentID(leaveofAbsenceRequest.getRespondentID());
            return leaveofAbsenceRequestRepository.save(existingLeaveofAbsenceRequest);  
        }
        return null;  
    }

    public LeaveofAbsenceRequest getLeaveofAbsenceRequestByID(Integer id) {
        return leaveofAbsenceRequestRepository.findById(id).orElse(null); 
    }

    public List<LeaveofAbsenceRequest> viewRequests(){
        List<LeaveofAbsenceRequest> leaveofAbsenceRequests = getAllLeaveofAbsenceRequests();
        ArrayList<LeaveofAbsenceRequest> output = new ArrayList<>();
        for (LeaveofAbsenceRequest leaveofAbsenceRequest : leaveofAbsenceRequests){
            if (leaveofAbsenceRequest.getPending()) {
                output.add(leaveofAbsenceRequest);
            }
        }
        return output;
    }

    public void createLeaveofAbsenceRequest(int id, String requestDate, String message, ArrayList<String> dates){
        LeaveofAbsenceRequest leaveofAbsenceRequest = createLeaveofAbsenceRequest(id, message, dates);
        notificationService.createNotification(requestDate, leaveofAbsenceRequest.getID(), 0);
    }

    public boolean approveLeaveofAbsenceRequest(String requestDate, int chairID, int leaveofAbsenceID){
        LeaveofAbsenceRequest leaveofAbsenceRequest = getLeaveofAbsenceRequestByID(leaveofAbsenceID);
        TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
        leaveofAbsenceRequest.setRespondentID(chairID);
        leaveofAbsenceRequest.setPending(false);
        notificationService.createNotification(requestDate, leaveofAbsenceRequest.getID(), 1);
        ArrayList<String> currentDates = ta.getOnLeaveDates();
        ArrayList<String> additionalDates = leaveofAbsenceRequest.getDates();
        boolean duplicate;
        for (String stringOutside : additionalDates){
            duplicate = false;
            int newIndex = 0;
            for (String stringInside : currentDates){
                if (stringOutside.equals(stringInside)){
                    duplicate = true;
                    break;
                }
                else if (compare(stringOutside, stringInside)){
                    newIndex++;
                }
            }
            if (!duplicate){
                currentDates.add(newIndex, stringOutside);
            }

        }
        ta.setOnLeaveDates(currentDates);
        taService.updateTAByID(leaveofAbsenceRequest.getOwnerID(), ta);
        updateLeaveofAbsenceRequestByID(leaveofAbsenceID, leaveofAbsenceRequest);
        return true;
    }

    public boolean rejectLeaveofAbsenceRequest(String requestDate, int chairID, int leaveofAbsenceID){
        LeaveofAbsenceRequest leaveofAbsenceRequest = getLeaveofAbsenceRequestByID(leaveofAbsenceID);
        notificationService.createNotification(requestDate, leaveofAbsenceID, 2);
        leaveofAbsenceRequest.setPending(false);
        leaveofAbsenceRequest.setRespondentID(chairID);
        updateLeaveofAbsenceRequestByID(leaveofAbsenceID, leaveofAbsenceRequest);
        return false;
    }

    private boolean compare(String stringOutside, String stringInside) {
        if (Integer.valueOf(stringOutside.substring(6)) > Integer.valueOf(stringInside.substring(6))){
            return true;
        }
        else if (Integer.valueOf(stringOutside.substring(3,5)) > Integer.valueOf(stringInside.substring(3,5))){
            return true;
        }
        else if (Integer.valueOf(stringOutside.substring(0, 2)) > Integer.valueOf(stringInside.substring(0,2))){
            return true;
        }
        return false;
    }
}
