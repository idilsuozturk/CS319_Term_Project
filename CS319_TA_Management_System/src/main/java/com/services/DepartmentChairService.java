package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.DepartmentChair;
import com.entities.LeaveofAbsenceRequest;
import com.entities.TA;
import com.repositories.DepartmentChairRepository;

@Service
public class DepartmentChairService {
    private final DepartmentChairRepository departmentChairRepository;

    private final LeaveofAbsenceRequestService leaveofAbsenceRequestService;

    private final NotificationService notificationService;

    private final TAService taService;

    public DepartmentChairService(DepartmentChairRepository departmentChairRepository, LeaveofAbsenceRequestService leaveofAbsenceRequestService, NotificationService notificationService, TAService taService) {
        this.departmentChairRepository = departmentChairRepository;
        this.leaveofAbsenceRequestService = leaveofAbsenceRequestService;
        this.notificationService = notificationService;
        this.taService = taService;
    }

    public List<DepartmentChair> getAllDepartmentChair() {
        return departmentChairRepository.findAll();
    }

    public DepartmentChair createDepartmentChair(String name, String email, String username, String password, String departmentCode) {
        DepartmentChair newDepartmentChair = new DepartmentChair(name,email, username, password, departmentCode);
        return departmentChairRepository.save(newDepartmentChair);
    }

    public DepartmentChair getDepartmentChairById(Integer id) {
        return departmentChairRepository.findById(id).orElse(null);
    }

    public void deleteDepartmentChairById(Integer id) {
        departmentChairRepository.deleteById(id);
    }

    public DepartmentChair updateDepartmentChair(Integer id, DepartmentChair departmentChair) {
        DepartmentChair existingDepartmentChair = departmentChairRepository.findById(id).orElse(null);
        if (existingDepartmentChair != null) {
            existingDepartmentChair.setName(departmentChair.getName());
            existingDepartmentChair.setEmail(departmentChair.getEmail());
            existingDepartmentChair.setUsername(departmentChair.getUsername());
            existingDepartmentChair.setPassword(departmentChair.getPassword());
            existingDepartmentChair.setDepartmentCode(departmentChair.getDepartmentCode());
            
            return departmentChairRepository.save(existingDepartmentChair);
        }
        return null;
    }

    public ArrayList<String> viewRequests(){
        List<LeaveofAbsenceRequest> leaveofAbsenceRequests = leaveofAbsenceRequestService.getAllLeaveofAbsenceRequests();
        ArrayList<String> output = new ArrayList<>();
        for (LeaveofAbsenceRequest leaveofAbsenceRequest : leaveofAbsenceRequests){
            if (leaveofAbsenceRequest.getPending()) {
                output.add(convertLeaveofAbsenceRequestToString(leaveofAbsenceRequest));
            }
        }
        return output;
    }

    public boolean approveLeaveofAbsenceRequest(String requestDate, int chairID, int leaveofAbsenceID){
        LeaveofAbsenceRequest leaveofAbsenceRequest = leaveofAbsenceRequestService.getLeaveofAbsenceRequestByID(leaveofAbsenceID);
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
        leaveofAbsenceRequestService.updateLeaveofAbsenceRequestByID(leaveofAbsenceID, leaveofAbsenceRequest);
        return true;
    }

    public boolean rejectLeaveofAbsenceRequest(String requestDate, int chairID, int leaveofAbsenceID){
        LeaveofAbsenceRequest leaveofAbsenceRequest = leaveofAbsenceRequestService.getLeaveofAbsenceRequestByID(leaveofAbsenceID);
        notificationService.createNotification(requestDate, leaveofAbsenceID, 2);
        leaveofAbsenceRequest.setPending(false);
        leaveofAbsenceRequest.setRespondentID(chairID);
        leaveofAbsenceRequestService.updateLeaveofAbsenceRequestByID(leaveofAbsenceID, leaveofAbsenceRequest);
        return false;
    }

    private String convertLeaveofAbsenceRequestToString(LeaveofAbsenceRequest leaveofAbsenceRequest) {
        if (leaveofAbsenceRequest == null){
            return "No Leave of Absence Request";
        }
        TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
        String output = "";
        if (ta != null){
            output = leaveofAbsenceRequest.getMessage() + "\nSender TA Information:" + "\nTA name: " + ta.getName() + "\nTA ID: "
            + ta.getUsername() + "\nThe TA's Message:\n" + leaveofAbsenceRequest.getMessage() + "\nThe Dates TA wants to be on leave: " + 
            leaveofAbsenceRequest.getDates().get(0);
            for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                output += ", "+ leaveofAbsenceRequest.getDates().get(i);
            }
        }
        return output;
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
