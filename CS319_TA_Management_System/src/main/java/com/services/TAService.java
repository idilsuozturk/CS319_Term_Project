package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.TA;
import com.repositories.TARepository;


@Service
public class TAService {
   
    private final TARepository taRepository;

    public TAService(TARepository taRepository) {
        this.taRepository = taRepository;
    }

    public List<TA> getAllTAs() {
        return taRepository.findAll();
    }

    public TA createTA(String firstName, String lastName, String email, String username, String password, boolean master, Integer advisorID, boolean partTime, String departmentCode) {
        TA newTA = new TA(firstName, lastName, email, username, password, master, advisorID, partTime, departmentCode);
        return taRepository.save(newTA);
    }

    public TA getTAByID(Integer id) {
        return taRepository.findById(id).orElse(null);
    }

    public void deleteTAByID(Integer id) {
        taRepository.deleteById(id);
    }

    public TA updateTAByID(Integer id, TA ta) {
        TA existingTA = taRepository.findById(id).orElse(null);
        if (existingTA != null) {
            existingTA.setFirstName(ta.getFirstName());
            existingTA.setLastName(ta.getLastName());
            existingTA.setEmail(ta.getEmail());
            existingTA.setUsername(ta.getUsername());
            existingTA.setPassword(ta.getPassword());
            existingTA.setCoursesAssisted(ta.getCoursesAssisted());
            existingTA.setCoursesTaken(ta.getCoursesTaken());
            existingTA.setAdvisorID(ta.getAdvisorID());
            existingTA.setTotalWorkload(ta.getTotalWorkload());
            existingTA.setProctoringAssignmentIDs(ta.getProctoringAssignmentIDs());
            existingTA.setMaster(ta.getMaster());
            existingTA.setOnLeaveDates(ta.getOnLeaveDates());
            existingTA.setLastAutomaticSwapTAID(ta.getLastAutomaticSwapTAID());
            existingTA.setLastAutomaticSwapProctoringAssignmentID(ta.getLastAutomaticSwapProctoringAssignmentID());
            existingTA.setSwapCount(ta.getSwapCount());
            existingTA.setPartTime(ta.getPartTime());
            existingTA.setDepartmentCode(ta.getDepartmentCode());  
            return taRepository.save(existingTA);
        }
        return null;
    }

    public void changeMode(int id, byte newMode){
        TA ta = taRepository.findById(id).orElse(null);
        if (ta != null){
            ta.setMode(newMode);
            updateTAByID(id, ta);
        }
    }

    public String[] viewSchedule(Integer id, int day) {
        TA ta = taRepository.findById(id).orElse(null);
        if (ta != null){
            if (ta.getMode() == 0){
                String [] dailySchedule = new String[14];
                for (int i = day * 14; i < (i + 1) * 14; i++){
                    dailySchedule[i - day * 14] = convertScheduleCellToString(ta.getSchedule()[i]);
                }
                return dailySchedule;
            }
            else if (ta.getMode() == 1){
                String[] weeklySchedule = new String[98];
                for (int i = 0; i < 98; i++){
                    weeklySchedule[i] = convertScheduleCellToString(ta.getSchedule()[i]);
                }
                return weeklySchedule;
            }
            else {
                String[] monthlySchedule = new String[392];
                for (int i = 0; i < 98; i++){
                    monthlySchedule[i] = convertScheduleCellToString(ta.getSchedule()[i]);
                }
                for (int i = 98; i < 392; i++){
                    monthlySchedule[i] = monthlySchedule[i%98];
                }
                return monthlySchedule;
            }
        }
        return null;
    }

    public List<TA> showTAs(Integer taID){
        List<TA> allTAs = getAllTAs();
        List<TA> output = new ArrayList<>();
        for (TA ta : allTAs){
            System.out.println(ta.getId());
            if (!(ta.getId() == taID)){
                output.add(ta);
            }
        }
        return output;
    }

    public int viewTotalWorkload(Integer id) {
        TA ta = taRepository.findById(id).orElse(null);
        if (ta != null){
            return ta.getTotalWorkload();
        }
        return -1;
    }

    private String convertScheduleCellToString(String string) {
        if (string == null){
            return null;
        }
        if (string.charAt(0) == 'S'){
            if (string.charAt(1) == 'F'){
                return string.substring( 2) + "\nFace-to-face Lecture" + "\n(Spare Hour)";
            }
            else {
                return string.substring( 2) + "\nOnline Lecture" + "\n(Spare Hour)";
            }
        }
        else if (string.charAt(0) == 'X'){
            if (string.charAt(1) == 'F'){
                return string.substring( 2) + "\nFace-to-face Lab/Studio";
            }
            else {
                return string.substring( 2) + "\nOnline Lab/Studio";
            }
        }
        else if (string.charAt(0) == 'R'){
            return string.substring(1) + "\nRecitation";
        }
        else {
            if (string.charAt(1) == 'F'){
                return string.substring( 2) + "\nFace-to-face Lecture";
            }
            else {
                return string.substring( 2) + "\nOnline Lecture";
            }
        }
    }

    /*private String convertManuelSwapRequestToString(ManuelSwapRequest manuelSwapRequest) {
        if (manuelSwapRequest == null){
            return "No Manuel Swap Request";
        }
        TA ta = getTAByID(manuelSwapRequest.getOwnerID());
        String output = "";
        if (ta != null){
            output = "\nSender TA Information:" + "\nTA name: " + ta.getName() + "\nTA ID: " + 
            ta.getUsername() + "\nThe Proctoring Assignment Information TA wants to swap with:\n" + 
            convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getOwnerProctoringAssignmentID()))
            + "\nThe Proctoring Assignment Information TA wants to swap to:\n" +
            convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getReceiverProctoringAssignmentID()))
            + "\nThe TA's Message:\n" + manuelSwapRequest.getMessage();

        }
        return output;
    }*/
}


