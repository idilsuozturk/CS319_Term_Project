package com.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.entities.Classroom;
import com.entities.Course;
import com.entities.Dean;
import com.entities.Instructor;
import com.entities.ProctoringAssignment;
import com.entities.Student;
import com.entities.TA;
import com.entities.User;
import com.repositories.ProctoringAssignmentRepository;
import java.util.Random;
@Service
public class ProctoringAssignmentService {
    private final ProctoringAssignmentRepository proctoringAssignmentRepository;

    private final ClassroomService classroomService;

    private final CoursesService coursesService;

    private final DeanService deanService;

    private final InstructorService instructorService;

    private final StudentService studentService;

    private final TAService taService;

    private int check;

    public ProctoringAssignmentService(ProctoringAssignmentRepository proctoringAssignmentRepository, ClassroomService classroomService, StudentService studentService, CoursesService coursesService, InstructorService instructorService, TAService taService, UserService userService, DeanService deanService) {
        this.proctoringAssignmentRepository = proctoringAssignmentRepository;
        this.classroomService = classroomService; 
        this.coursesService = coursesService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.taService = taService;
        this.deanService = deanService;
    }
    public List<ProctoringAssignment> getAllProctoringAssignments() {
        return proctoringAssignmentRepository.findAll();  
    }   

    public ProctoringAssignment createProctoringAssignment(int year, int month, int day, int startTime, int endTime, int classroomID, int courseID, int proctorID) {
        return proctoringAssignmentRepository.save(new ProctoringAssignment(year, month, day, startTime, endTime, classroomID, courseID, proctorID));  
    }

 
    public void deleteProctoringAssignmentByID(Integer id) {
        proctoringAssignmentRepository.deleteById(id);  
    }

    public ProctoringAssignment updateProctoringAssignmentByID(Integer id, ProctoringAssignment proctoringAssignment) {
        ProctoringAssignment existingProctoringAssignment = proctoringAssignmentRepository.findById(id).orElse(null);  
        if (existingProctoringAssignment != null) {
            existingProctoringAssignment.setYear(proctoringAssignment.getYear());
            existingProctoringAssignment.setMonth(proctoringAssignment.getMonth());
            existingProctoringAssignment.setDay(proctoringAssignment.getDay());
            existingProctoringAssignment.setStartTime(proctoringAssignment.getStartTime());
            existingProctoringAssignment.setEndTime(proctoringAssignment.getEndTime());
            existingProctoringAssignment.setClassroomID(proctoringAssignment.getClassroomID());
            existingProctoringAssignment.setCourseID(proctoringAssignment.getCourseID());
            existingProctoringAssignment.setProctorID(proctoringAssignment.getProctorID());
            return proctoringAssignmentRepository.save(existingProctoringAssignment);  
        }
        return null;  
    }

    public ProctoringAssignment getProctoringAssignmentByID(Integer id) {
        return proctoringAssignmentRepository.findById(id).orElse(null); 
    }

    public List<ProctoringAssignment> showPossibleProctoringAssignments(Integer receiverID, Integer proctoringAssignmentID) {
        ProctoringAssignment proctoringAssignment = getProctoringAssignmentByID(proctoringAssignmentID);
        TA requester = taService.getTAByID(proctoringAssignment.getProctorID());
        TA receiver = taService.getTAByID(receiverID);
        if (proctoringAssignment == null || requester == null || receiver == null){
            return null;
        }
        ArrayList<ProctoringAssignment> output = new ArrayList<ProctoringAssignment>();
        if (isAvailable(receiverID, proctoringAssignmentID, -1, 0)) output.add(null);
        for (int id : receiver.getProctoringAssignmentIDs()){
            if (isAvailable(requester.getId(), id, proctoringAssignmentID, 0)){
                ProctoringAssignment newProctoringAssignment = getProctoringAssignmentByID(id);
                output.add(newProctoringAssignment);
            }
        }
        return output;
    }

    public String[] getExamList(Integer id, boolean random){
        ProctoringAssignment proctoringAssignment = getProctoringAssignmentByID(id);
        if (proctoringAssignment == null){
            return null;
        }
        Classroom classroom = classroomService.getClassroomByID(proctoringAssignment.getClassroomID());
        if (classroom == null) {
            return null;
        }
        String[] array = new String[classroom.getExamList().length];
        for (int i = 0; i < array.length; i++){
            Student student = studentService.getStudentByStudentID(classroom.getExamList()[i]);
            array[i] = student.getName();
        }
        if (random){
            Random rand = new Random();
            for (int i = array.length - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        else {
            Arrays.sort(array);
        }
        return array;
    }
    public List<ProctoringAssignment> viewProctoringAssignments(Integer id) {
        TA ta = taService.getTAByID(id);
        if (ta != null){
            ArrayList<ProctoringAssignment> output = new ArrayList<ProctoringAssignment>();
            for (int i : ta.getProctoringAssignmentIDs()){
                ProctoringAssignment proctoringAssignment = getProctoringAssignmentByID(i);
                output.add(proctoringAssignment);
            }
            return output;
        }
        return null;
    }

    public int automaticProctoringAssignmentByInstructor(String courseName, Integer id, int restriction){
        Instructor instructor = instructorService.getInstructorByID(id);
        if (instructor == null){
            return -1;
        }
        Course testCourse = coursesService.getCourseByCode(courseName);
        if (testCourse == null) {
            return 0;
        }
        List<ProctoringAssignment> proctoringAssignments = getAllProctoringAssignments();
        List<TA> tas = taService.getAllTAs();
        List<TA> tasFromSameDepartment = new ArrayList<>();
        for (TA ta : tas){
            if (ta.getDepartmentCode() == instructor.getDepartmentCode()){
                tasFromSameDepartment.add(ta);
            }
        }
        List<ProctoringAssignment> specificProctoringAssignments = new ArrayList<>();
        for (ProctoringAssignment pa : proctoringAssignments){
            Course course = coursesService.getCourseByID(pa.getCourseID());
            if (course == null){
                continue;
            }
            if (course.getCode() == courseName){
                specificProctoringAssignments.add(pa);
            }
        }
        if (specificProctoringAssignments.size() == 0) {
            return -2;
        }
        ArrayList<TA> sortedTAs = new ArrayList<>(tasFromSameDepartment);
        if (restriction == 1 || restriction == 3 || restriction == 6 || restriction == 7){
            sortedTAs.sort(Comparator.comparingInt(ta -> getPriority(courseName, ta, specificProctoringAssignments.get(0).getDay(), specificProctoringAssignments.get(0).getMonth(), specificProctoringAssignments.get(0).getYear(), true)));
        }
        else {
            sortedTAs.sort(Comparator.comparingInt(ta -> getPriority(courseName, ta, specificProctoringAssignments.get(0).getDay(), specificProctoringAssignments.get(0).getMonth(), specificProctoringAssignments.get(0).getYear(), false)));
        }
        Collections.reverse(sortedTAs);
        for (ProctoringAssignment pa : specificProctoringAssignments){
            boolean isFound = false;
            for (int i = 0; i < sortedTAs.size(); i++){
                if (isAvailable(sortedTAs.get(i).getId(), pa.getID(), -1, restriction)){
                    ArrayList<Integer> proctoringAssignmentIDs = sortedTAs.get(i).getProctoringAssignmentIDs();
                    proctoringAssignmentIDs.add(pa.getID());
                    sortedTAs.get(i).setProctoringAssignmentIDs(proctoringAssignmentIDs);
                    taService.updateTAByID(sortedTAs.get(i).getId(), sortedTAs.get(i));
                    pa.setProctorID(sortedTAs.get(i).getId());
                    updateProctoringAssignmentByID(pa.getID(), pa);
                    sortedTAs.get(i).setTotalWorkload(sortedTAs.get(i).getTotalWorkload() + 4);
                    isFound = true;
                    break;
                }
            }
            if (!isFound){
                return 1;
            }
        }
        return 2;
    }

    public String manualProctoringAssignmentByInstructor(ProctoringAssignment proctoringAssignment, Integer taID, Integer instructorID){
        if (proctoringAssignment == null){
            return "Null";
        }
        Instructor ins = instructorService.getInstructorByID(instructorID);
        TA ta = taService.getTAByID(taID);
        if (ta == null || ins == null){
            return "Null";
        }
        if (ta.getDepartmentCode() != ins.getDepartmentCode()){
            return "The TA and You do not belong to the same department, please try again!";
        }
        if (isAvailable(taID, proctoringAssignment.getID(), -1, 0)){
            return "OK";
        }
        else if (check == 1){
            return "The Proctoring Assignments course is taken by this TA, please try again!";
        }
        else if (check == 2){
            return "The TA is on Leave of Absence at the requested date, please try again!";
        }
        else if (check == 3){
            return "The TA's schedule is not available, please try again!";
        }
        else {
            return "The TA is assigned to another Proctoring Assignment for the date you have requested, please try again!";
        }
    }

    public int automaticProctoringAssignmentByDean(String courseName, Integer taID, int restriction, String[] selectedDepartments){
        Course testCourse = coursesService.getCourseByCode(courseName);
        if (testCourse == null) {
            return 0;
        }
        List<ProctoringAssignment> proctoringAssignments = getAllProctoringAssignments();
        List<TA> tas = taService.getAllTAs();
        List<TA> tasAvailable = new ArrayList<>();
        for (TA ta : tas){
            boolean check = false;
            for (String i : selectedDepartments){
                if (i.equals(ta.getDepartmentCode())) {
                    check = true;
                    break;
                }
            }
            if (check){
                tasAvailable.add(ta);
            }
        }
        List<ProctoringAssignment> specificProctoringAssignments = new ArrayList<>();
        for (ProctoringAssignment pa : proctoringAssignments){
            Course course = coursesService.getCourseByID(pa.getCourseID());
            if (course == null){
                continue;
            }
            if (course.getCode() == courseName){
                specificProctoringAssignments.add(pa);
            }
        }
        if (specificProctoringAssignments.size() == 0) {
            return -2;
        }
        ArrayList<TA> sortedTAs = new ArrayList<>(tas);
        if (restriction == 1 || restriction == 3 || restriction == 6 || restriction == 7){
            sortedTAs.sort(Comparator.comparingInt(ta -> getPriority(courseName, ta, specificProctoringAssignments.get(0).getDay(), specificProctoringAssignments.get(0).getMonth(), specificProctoringAssignments.get(0).getYear(), true)));
        }
        else {
            sortedTAs.sort(Comparator.comparingInt(ta -> getPriority(courseName, ta, specificProctoringAssignments.get(0).getDay(), specificProctoringAssignments.get(0).getMonth(), specificProctoringAssignments.get(0).getYear(), false)));
        }
        Collections.reverse(sortedTAs);
        for (ProctoringAssignment pa : specificProctoringAssignments){
            boolean isFound = false;
            for (int i = 0; i < sortedTAs.size(); i++){
                if (isAvailable(sortedTAs.get(i).getId(), pa.getID(), -1, restriction)){
                    ArrayList<Integer> proctoringAssignmentIDs = sortedTAs.get(i).getProctoringAssignmentIDs();
                    proctoringAssignmentIDs.add(pa.getID());
                    sortedTAs.get(i).setProctoringAssignmentIDs(proctoringAssignmentIDs);
                    taService.updateTAByID(sortedTAs.get(i).getId(), sortedTAs.get(i));
                    pa.setProctorID(sortedTAs.get(i).getId());
                    updateProctoringAssignmentByID(pa.getID(), pa);
                    sortedTAs.get(i).setTotalWorkload(sortedTAs.get(i).getTotalWorkload() + 4);
                    isFound = true;
                    break;
                }
            }
            if (!isFound){
                return 1;
            }
        }
        return 2;
    }

    public String manualProctoringAssignmentByDean(ProctoringAssignment proctoringAssignment, Integer taID, String[] selectedDepartments){
        if (proctoringAssignment == null){
            return "Null";
        }
        TA ta = taService.getTAByID(taID);
        if (ta == null){
            return "Null";
        }
        boolean specialCheck = true;
        for (String i : selectedDepartments){
            if (i.equals(ta.getDepartmentCode())){
                specialCheck = false;
                break;
            }
        }
        if (specialCheck){
            return "The TA does not belong to the given department(s), please try again!";
        }
        if (isAvailable(taID, proctoringAssignment.getID(), -1, 0)){
            return "OK";
        }
        else if (check == 1){
            return "The Proctoring Assignments course is taken by this TA, please try again!";
        }
        else if (check == 2){
            return "The TA is on Leave of Absence at the requested date, please try again!";
        }
        else if (check == 3){
            return "The TA's schedule is not available, please try again!";
        }
        else {
            return "The TA is assigned to another Proctoring Assignment for the date you have requested, please try again!";
        }
    }
    
    public boolean isAvailable(int taID, Integer proctoringAssignmentID, Integer ignore, int restriction){
        ProctoringAssignment proctoringAssignment = getProctoringAssignmentByID(proctoringAssignmentID);
        ProctoringAssignment proctoringAssignmentToBeIgnored = getProctoringAssignmentByID(ignore);
        TA ta = taService.getTAByID(taID);
        if (proctoringAssignment == null || ta == null){
            check = 0;
            return false;
        }
        if (isPresent(proctoringAssignment.getCourseID(), ta.getCoursesTaken())){
            check = 1;
            return false;   
        }
        int day = proctoringAssignment.getDay();
        int month = proctoringAssignment.getMonth();
        int year = proctoringAssignment.getYear();
        for (String j : ta.getOnLeaveDates()){
            if (Integer.valueOf(j.substring(0,2)) == day &&
                Integer.valueOf(j.substring(3, 5)) == month && 
                Integer.valueOf(j.substring(6)) == year) {
                    check = 2;
                    return false;
                }
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
                check = 3;
                return false;
            }   
        }
        for (int id : ta.getProctoringAssignmentIDs()){
            if (proctoringAssignmentToBeIgnored != null && id == proctoringAssignmentToBeIgnored.getID()) continue;
            ProctoringAssignment test = getProctoringAssignmentByID(id);
            if (test != null) {
                int testDay = test.getDay();
                if (day == testDay){
                    int testMonth = test.getMonth();
                    if (month == testMonth){
                        int testYear = test.getYear();
                        if (year == testYear){
                            int testStart = test.getStartTime();
                            int testEnd = test.getEndTime();
                            if (testStart > start && end > testStart || start > testStart && testEnd > start) {
                                check = 4;
                                return false;
                            }
                        }
                    }
                }
            }
        }
        check = -1;
        return true;
    }
    
    private boolean isPresent(int courseID, ArrayList<Integer> coursesTaken) {
        for (int id : coursesTaken){
            if (courseID == id){
                return false;
            }
        }
        return true;
    }

    private int getPriority(String courseName, TA ta, int day, int month, int year, boolean care){
        int priority = 0;
        for (int i : ta.getCoursesAssisted()){
            Course course = coursesService.getCourseByID(i);
            if (course == null){
                continue;
            }
            if (course.getCode().equals(courseName)){
                priority += 50;
            }
        }
        int equalPunishment = 60;
        int consecutivePunishment = 30;
        if (care) {
            for (int i : ta.getProctoringAssignmentIDs()){
                ProctoringAssignment pa = getProctoringAssignmentByID(i);
                if (pa == null){
                    continue;
                }
                LocalDate localDateOne = LocalDate.of(year, month, day);
                LocalDate localDateTwo = LocalDate.of(pa.getYear(), pa.getMonth(), pa.getDay());
                long daysBetween = ChronoUnit.DAYS.between(localDateOne, localDateTwo);
                if (localDateOne.equals(localDateTwo)){
                    priority -=  equalPunishment;
                }
                else if (daysBetween == -1 || daysBetween == 1){
                    priority -= consecutivePunishment;
                }
            }
        }
        return priority - ta.getTotalWorkload();
    }
}

