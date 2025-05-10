package com.services;

import org.springframework.stereotype.Service;

import com.entities.TA;
import com.entities.Course;
import com.entities.Instructor;
import com.entities.ProctoringAssignment;
import com.repositories.TARepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class TAService {
   
    private final TARepository taRepository;

    private final CoursesService courseService; 

    private final InstructorService instructorService;

    private final ProctoringAssignmentService proctoringAssignmentService;

    public TAService(TARepository taRepository, CoursesService courseService, InstructorService instructorService, ProctoringAssignmentService proctoringAssignmentService) {
        this.taRepository = taRepository;
        this.courseService = courseService; 
        this.instructorService = instructorService;
        this.proctoringAssignmentService = proctoringAssignmentService;
    }

    public List<TA> getAllTAs() {
        return taRepository.findAll();
    }

    public TA createTA(String name, String username, String email, String password, boolean master, Integer advisorID) {
        TA newTA = new TA(name, email, username, password, master, advisorID);
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
            return taRepository.save(existingTA);
        }
        return null;
    }

    public boolean addCourse(Integer courseID, Integer taID, boolean taken){
        if (taken){
            Course course = courseService.getCourseByID(courseID);
            if (course != null){
                TA ta = taRepository.findById(taID).orElse(null);
                if (ta != null){
                    String[] courseSchedule = course.getSchedule();
                    String[] taSchedule = ta.getSchedule();
                    for (int i = 0; i < 98; i++){
                        if (courseSchedule[i] != null){
                            if (taSchedule[i] != null){
                                return false;
                            }
                            else {
                                taSchedule[i] = courseSchedule[i] + course.getCode() + " - " + course.getSection();
                            }
                        }
                    }
                    ArrayList<Integer> coursesTaken = ta.getCoursesTaken();
                    coursesTaken.add(courseID);
                    ta.setCoursesTaken(coursesTaken);
                    updateTAByID(taID, ta);
                    return true;
                }
                return false;
            }
            return false;
        }
        else {
            Course course = courseService.getCourseByID(courseID);
            if (course != null){
                TA ta = taRepository.findById(taID).orElse(null);
                if (ta != null){
                    ArrayList<Integer> coursesAssisted = ta.getCoursesAssisted();
                    for (int i : coursesAssisted){
                        if (i == courseID){
                            return false;
                        }
                    }
                    coursesAssisted.add(courseID);
                    ta.setCoursesAssisted(coursesAssisted);
                    updateTAByID(taID, ta);
                    Instructor instructor = instructorService.getInstructorByID(course.getInstructorID());
                    if (instructor != null){
                        ArrayList<Integer> taIDs = instructor.getTaIDs();
                        taIDs.add(taID);
                        instructor.setTaIDs(taIDs);
                        instructorService.updateInstructorByID(instructor.getId(), instructor);
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
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
        allTAs.remove(getTAByID(taID));
        return allTAs;
    }

    public List<ProctoringAssignment> showPossibleProctoringAssignments(Integer receiverID, Integer proctoringAssignmentID) {
        ProctoringAssignment proctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(proctoringAssignmentID);
        TA requester = taRepository.findById(proctoringAssignment.getProctorID()).orElse(null);
        TA receiver = taRepository.findById(receiverID).orElse(null);
        if (proctoringAssignment == null || requester == null || receiver == null){
            return null;
        }
        ArrayList<ProctoringAssignment> output = new ArrayList<ProctoringAssignment>();
        if (isAvailable(receiverID, proctoringAssignmentID, -1)) output.add(null);
        for (int id : receiver.getProctoringAssignmentIDs()){
            if (isAvailable(requester.getId(), id, proctoringAssignmentID)){
                ProctoringAssignment newProctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(id);
                output.add(newProctoringAssignment);
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

    public List<ProctoringAssignment> viewProctoringAssignments(Integer id) {
        TA ta = taRepository.findById(id).orElse(null);
        if (ta != null){
            ArrayList<ProctoringAssignment> output = new ArrayList<ProctoringAssignment>();
            for (int i : ta.getProctoringAssignmentIDs()){
                ProctoringAssignment proctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(i);
                output.add(proctoringAssignment);
            }
            return output;
        }
        return null;
    }

    public boolean isAvailable(int taID, Integer proctoringAssignmentID, Integer ignore){
        ProctoringAssignment proctoringAssignment = proctoringAssignmentService.getProctoringAssignmentByID(proctoringAssignmentID);
        ProctoringAssignment proctoringAssignmentToBeIgnored = proctoringAssignmentService.getProctoringAssignmentByID(ignore);
        TA ta = taRepository.findById(taID).orElse(null);
        if (proctoringAssignment == null || ta == null){
            return false;
        }
        int day = proctoringAssignment.getDay();
        int month = proctoringAssignment.getMonth();
        int year = proctoringAssignment.getYear();
        for (String j : ta.getOnLeaveDates()){
            if (Integer.valueOf(j.substring(0,2)) == day &&
                Integer.valueOf(j.substring(3, 5)) == month && 
                Integer.valueOf(j.substring(6)) == year) return false;
        }
        int start = proctoringAssignment.getStartTime();
        int end = proctoringAssignment.getEndTime();
        int indexStart = start / 100;
        int indexEnd = end / 100;
        if (start % 100 <= 30){
            indexStart -= 9;
        }
        else {
            indexStart -= 8;
        }
        if (end % 100 <= 30){
            indexEnd -= 9;
        }
        else {
            indexEnd -= 8;
        }
        for (int i = indexStart; i <= indexEnd;i++){
            if (ta.getSchedule()[(day - 1) * 14 + i] == null){
                return false;
            }   
        }
        for (int id : ta.getProctoringAssignmentIDs()){
            if (proctoringAssignmentToBeIgnored != null && id == proctoringAssignmentToBeIgnored.getID()) continue;
            ProctoringAssignment test = proctoringAssignmentService.getProctoringAssignmentByID(id);
            if (test != null) {
                int testDay = test.getDay();
                if (day == testDay){
                    int testMonth = test.getMonth();
                    if (month == testMonth){
                        int testYear = test.getYear();
                        if (year == testYear){
                            int testStart = test.getStartTime();
                            int testEnd = test.getEndTime();
                            if (testStart > start && end > testStart || start > testStart && testEnd > start) return false;
                        }
                    }
                }
            }
        }
        return true;
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


