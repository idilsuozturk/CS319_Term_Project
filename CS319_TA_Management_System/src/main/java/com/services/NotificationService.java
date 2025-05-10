package com.services;


import org.springframework.stereotype.Service;

import com.entities.AutomaticSwapRequest;
import com.entities.Course;
import com.entities.DepartmentChair;
import com.entities.DepartmentStaff;
import com.entities.Instructor;
import com.entities.LeaveofAbsenceRequest;
import com.entities.ManualSwapRequest;
import com.entities.Notification;
import com.entities.ProctoringAssignment;
import com.entities.Request;
import com.entities.RequestTypes;
import com.entities.TA;
import com.entities.TaskSubmissionRequest;
import com.entities.TaskTypes;
import com.repositories.NotificationRepository;
import com.repositories.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final RequestRepository requestRepository;

    private final CoursesService coursesService;

    private final DepartmentChairService departmentChairService;

    private final DepartmentStaffService departmentStaffService;

    private final InstructorService instructorService;

    private final ProctoringAssignmentService proctoringAssignmentService;

    private final TAService taService;

    public NotificationService(NotificationRepository notificationRepository, RequestRepository requestRepository, CoursesService coursesService, DepartmentChairService departmentChairService, DepartmentStaffService departmentStaffService, InstructorService instructorService, ProctoringAssignmentService proctoringAssignmentService, TAService taService) {
        this.notificationRepository = notificationRepository;
        this.requestRepository = requestRepository;
        this.coursesService = coursesService;
        this.departmentChairService = departmentChairService;
        this.departmentStaffService = departmentStaffService;
        this.instructorService = instructorService;
        this.proctoringAssignmentService = proctoringAssignmentService;
        this.taService = taService;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Integer notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    public Notification getNotificationByRequestIDAndStatusNot(Integer requestID, int status){
        return notificationRepository.findByRequestIDAndStatusNot(status, status).orElse(null);
    }

    public Notification createNotification(String requestDate, Integer requestID, int status){
        return notificationRepository.save(new Notification(requestDate, requestID, status));
    }

    public void deleteNotificationById(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public Notification updateNotification(Integer notificationId, Notification notification) {
        Notification existingNotification = notificationRepository.findById(notificationId).orElse(null);
        if (existingNotification != null) {
            existingNotification.setRequestDate(notification.getRequestDate());
            existingNotification.setRequestId(notification.getRequestId());
            return notificationRepository.save(existingNotification);
        }
        return null;
    }

    public List<String> viewNotificationsTA(Integer id) {
        ArrayList<String> output = new ArrayList<>();
        List<Notification> notifications = getAllNotifications();
        for (Notification notification : notifications){
            Request request = requestRepository.findById(id).orElse(null);
            if (request.getRequestType() == RequestTypes.AUTOMATIC_SWAP_REQUEST){
                AutomaticSwapRequest automaticSwapRequest = (AutomaticSwapRequest) request;
                if (id == automaticSwapRequest.getFirstTAID()){
                    TA ta = taService.getTAByID(automaticSwapRequest.getSecondTAID());
                    Instructor instructor = instructorService.getInstructorByID(automaticSwapRequest.getOwnerID());
                    ProctoringAssignment proctoringAssignmentOne = proctoringAssignmentService.getProctoringAssignmentByID(automaticSwapRequest.getFirstTAsProctoringAssignmentID());
                    ProctoringAssignment proctoringAssignmentTwo = proctoringAssignmentService.getProctoringAssignmentByID(automaticSwapRequest.getSecondTAsProctoringAssignmentID());
                    String newString = "The Instructor " + instructor.getName() + " initialized a Proctor Swap at " + 
                    notification.getRequestDate() + " between you and TA " + ta.getName() + 
                    ". Your proctoring assignment (for assignment details see below) and TA " + ta.getName() + 
                    "'s proctoring assignment (for assignment details see below) has been swapped.\nYour Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentOne)
                    + "\nOther TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentTwo) + "Instructor " + 
                    instructor.getName() + "'s Message:\n" + automaticSwapRequest.getMessage();
                    output.add(newString);
                }
                else if (id == automaticSwapRequest.getSecondTAID()){
                    TA ta = taService.getTAByID(automaticSwapRequest.getFirstTAID());
                    Instructor instructor = instructorService.getInstructorByID(automaticSwapRequest.getOwnerID());
                    ProctoringAssignment proctoringAssignmentOne = proctoringAssignmentService.getProctoringAssignmentByID(automaticSwapRequest.getSecondTAsProctoringAssignmentID());
                    ProctoringAssignment proctoringAssignmentTwo = proctoringAssignmentService.getProctoringAssignmentByID(automaticSwapRequest.getFirstTAsProctoringAssignmentID());
                    String newString = "The Instructor " + instructor.getName() + " initialized a Proctor Swap at " + 
                    notification.getRequestDate() + " between you and TA " + ta.getName() + 
                    ". Your proctoring assignment (for assignment details see below) and TA " + ta.getName() + 
                    "'s proctoring assignment (for assignment details see below) has been swapped.\nYour Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentOne)
                    + "\nOther TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentTwo) + "\nInstructor " +
                    instructor.getName() + "'s Message:\n" + automaticSwapRequest.getMessage();
                    output.add(newString);
                }
                else {
                    continue;
                }
            }
            else if (request.getRequestType() == RequestTypes.LEAVE_OF_ABSENCE_REQUEST){
                LeaveofAbsenceRequest leaveofAbsenceRequest = (LeaveofAbsenceRequest) request;
                if (leaveofAbsenceRequest.getOwnerID() == id){
                    if (notification.getStatus() == 0){
                        String newString = "You have sent a Leave of Absence request at " + notification.getRequestDate() + 
                        "for date(s) " + leaveofAbsenceRequest.getDates().get(0);
                        for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                            newString += ", " + i;
                        }
                        newString += ".\nYour Message:\n" + leaveofAbsenceRequest.getMessage();
                        output.add(newString);
                    }
                    else if (notification.getStatus() == 1){
                        String newString = "Your Leave of Absence request requested for date(s) " + leaveofAbsenceRequest.getDates().get(0);
                        for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                            newString += ", " + leaveofAbsenceRequest.getDates().get(i);
                        } 
                        newString += " has been approved at " + notification.getRequestDate() + ".\nYour Message was:\n" + leaveofAbsenceRequest.getMessage();
                        output.add(newString);
                    }
                    else {
                        String newString = "Your Leave of Absence request requested for date(s) " + leaveofAbsenceRequest.getDates().get(0);
                        for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                            newString += ", " + leaveofAbsenceRequest.getDates().get(i);
                        } 
                        newString += " has been rejected at " + notification.getRequestDate() + ".\nYour Message was:\n" + leaveofAbsenceRequest.getMessage();
                        output.add(newString);
                    }
                }
                else {
                    continue;
                }
                
            }
            else if (request.getRequestType() == RequestTypes.MANUEL_SWAP_REQUEST){
                ManualSwapRequest manuelSwapRequest = (ManualSwapRequest) request;
                if (manuelSwapRequest.getOwnerID() == id){
                    if (notification.getStatus() == 0){
                        TA ta = taService.getTAByID(manuelSwapRequest.getReceiverID());
                        String newString = "You have sent a Proctoring Swap Request to TA " + ta.getName() + " at " + notification.getRequestDate() +
                        ".\nYour Proctoring Assignment Information:\n" +
                        convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getOwnerProctoringAssignmentID())) + 
                        "\nOther TA's Proctoring Assignment Information:\n";
                        if (manuelSwapRequest.getReceiverProctoringAssignmentID() == -1){
                            newString += "No Proctoring Assignment.";
                        }
                        else {
                            newString += convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getReceiverProctoringAssignmentID()));
                        }
                        newString += "\nYour Message:\n" + manuelSwapRequest.getMessage();
                        output.add(newString);
                        
                    }
                    else if (notification.getStatus() == 1){
                        TA ta = taService.getTAByID(manuelSwapRequest.getReceiverID());
                        String newString = "Your Proctoring Swap Request to TA " + ta.getName() + " has been approved at " + notification.getRequestDate() + 
                        ".\nYour Proctoring Assignment Information:\n" +
                        convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getOwnerProctoringAssignmentID())) + 
                        "\nOther TA's Proctoring Assignment Information:\n";
                        if (manuelSwapRequest.getReceiverProctoringAssignmentID() == -1){
                            newString += "No Proctoring Assignment.";
                        }
                        else {
                            newString += convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getReceiverProctoringAssignmentID()));
                        }
                        newString += "\nYour Message was:\n" + manuelSwapRequest.getMessage();
                        output.add(newString);
                    }
                    else {
                        TA ta = taService.getTAByID(manuelSwapRequest.getReceiverID());
                        String newString = "Your Proctoring Swap Request to TA " + ta.getName() + " has been rejected at " + notification.getRequestDate() + 
                        ".\nYour Proctoring Assignment Information:\n" +
                        convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getOwnerProctoringAssignmentID())) + 
                        "\nOther TA's Proctoring Assignment Information:\n";
                        if (manuelSwapRequest.getReceiverProctoringAssignmentID() == -1){
                            newString += "No Proctoring Assignment.";
                        }
                        else {
                            newString += convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getReceiverProctoringAssignmentID()));
                        }
                        newString += "\nYour Message was:\n" + manuelSwapRequest.getMessage();
                        output.add(newString);
                    }
                }
                else if (manuelSwapRequest.getReceiverID() == id){
                    if (notification.getStatus() == 0){
                        TA ta = taService.getTAByID(manuelSwapRequest.getOwnerID());
                        String newString = "A Proctoring Swap Request has been sent to you by TA " + ta.getName() + " at " + notification.getRequestDate() +
                        ".\nYour Proctoring Assignment Information:\n";
                        if (manuelSwapRequest.getReceiverProctoringAssignmentID() == -1){
                            newString += "No Proctoring Assignment.";
                        }
                        else {
                            newString += convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getReceiverProctoringAssignmentID()));
                        }
                        newString += "\nOther TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getOwnerProctoringAssignmentID())) + 
                        "\nOther TA's Message:\n" + manuelSwapRequest.getMessage();
                        output.add(newString);
                    }
                    else if (notification.getStatus() == 1){
                        TA ta = taService.getTAByID(manuelSwapRequest.getReceiverID());
                        String newString = "You have approved the Swap Request sent to you by TA " + ta.getName() + " at " + notification.getRequestDate() + 
                        ".\nYour Proctoring Assignment Information:\n";
                        if (manuelSwapRequest.getReceiverProctoringAssignmentID() == -1){
                            newString += "No Proctoring Assignment.";
                        }
                        else {
                            newString += convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getReceiverProctoringAssignmentID()));
                        }
                        newString += "\nOther TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getOwnerProctoringAssignmentID())) + 
                        "\nOther TA's Message was:\n" + manuelSwapRequest.getMessage();
                        output.add(newString);
                    }
                    else {
                        TA ta = taService.getTAByID(manuelSwapRequest.getReceiverID());
                        String newString = "You have rejected the Swap Request sent to you by TA " + ta.getName() + " at " + notification.getRequestDate() + 
                        ".\nYour Proctoring Assignment Information:\n";
                        if (manuelSwapRequest.getReceiverProctoringAssignmentID() == -1){
                            newString += "No Proctoring Assignment.";
                        }
                        else {
                            newString += convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getReceiverProctoringAssignmentID()));
                        }
                        newString += "\nOther TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentService.getProctoringAssignmentByID(manuelSwapRequest.getOwnerProctoringAssignmentID())) + 
                        "\nOther TA's Message was:\n" + manuelSwapRequest.getMessage();
                        output.add(newString);
                    }
                }
                else {
                    continue;
                }
            }
            else {
                TaskSubmissionRequest taskSubmissionRequest = (TaskSubmissionRequest) request;
                if (notification.getStatus() == 0){
                    Course course = coursesService.getCourseByID(taskSubmissionRequest.getCourseID());
                    Instructor instructor = instructorService.getInstructorByID(course.getInstructorID());
                    String newString = "You have sent a Task Submission Request at " + notification.getRequestDate() + " for the course " + 
                    course.getCode() + ", to the Instructor " + instructor.getName() + ".\nTask Information:\n";
                    if (taskSubmissionRequest.getTaskType() == TaskTypes.GRADING){
                        newString += "Task Type: Grading\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.LAB){
                        newString += "Task Type: Lab\nTask Duration: " + taskSubmissionRequest.getDuration() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.OFFICE_HOUR){
                        newString += "Task Type: Office Hour\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.PROCTORING){
                        newString += "Task Type: Proctoring\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else {
                        newString += "Task Type: Recitation\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    newString += "\nYour Message:\n" + taskSubmissionRequest.getMessage();
                    output.add(newString);
                }
                else if (notification.getStatus() == 1){
                    Course course = coursesService.getCourseByID(taskSubmissionRequest.getCourseID());
                    Instructor instructor = instructorService.getInstructorByID(course.getInstructorID());
                    String newString = "Your Task Submission Request has been approved at " + notification.getRequestDate() + " for the course " + 
                    course.getCode() + ", by the Instructor " + instructor.getName() + ".\nTask Information:\n";
                    if (taskSubmissionRequest.getTaskType() == TaskTypes.GRADING){
                        newString += "Task Type: Grading\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.LAB){
                        newString += "Task Type: Lab\nTask Duration: " + taskSubmissionRequest.getDuration() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.OFFICE_HOUR){
                        newString += "Task Type: Office Hour\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.PROCTORING){
                        newString += "Task Type: Proctoring\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else {
                        newString += "Task Type: Recitation\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    newString += "\nYour Message was:\n" + taskSubmissionRequest.getMessage();
                    output.add(newString);
                }
                else {
                    Course course = coursesService.getCourseByID(taskSubmissionRequest.getCourseID());
                    Instructor instructor = instructorService.getInstructorByID(course.getInstructorID());
                    String newString = "Your Task Submission Request has been rejected at " + notification.getRequestDate() + " for the course " + 
                    course.getCode() + ", by the Instructor " + instructor.getName() + ".\nTask Information:\n";
                    if (taskSubmissionRequest.getTaskType() == TaskTypes.GRADING){
                        newString += "Task Type: Grading\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.LAB){
                        newString += "Task Type: Lab\nTask Duration: " + taskSubmissionRequest.getDuration() + "\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.OFFICE_HOUR){
                        newString += "Task Type: Office Hour\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else if (taskSubmissionRequest.getTaskType() == TaskTypes.PROCTORING){
                        newString += "Task Type: Proctoring\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    else {
                        newString += "Task Type: Recitation\nTask Date: " + taskSubmissionRequest.getTaskDate();
                    }
                    newString += "\nYour Message was:\n" + taskSubmissionRequest.getMessage();
                    output.add(newString);
                }
            }
        }
        return output;
    }

    public List<String> viewNotificationsDepartmentChair(int chairID){
        ArrayList<String> output = new ArrayList<>();
        List<Notification> notifications = getAllNotifications();
        for (Notification notification : notifications){
            Request request = requestRepository.findById(notification.getRequestId()).orElse(null);
            if (request.getRequestType() == RequestTypes.LEAVE_OF_ABSENCE_REQUEST){
                LeaveofAbsenceRequest leaveofAbsenceRequest = (LeaveofAbsenceRequest) request;
                if (notification.getStatus() == 0){
                    TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
                    String newString = "The TA " + ta.getName() + " has requested a Leave of Absence Request for the dates " + 
                    leaveofAbsenceRequest.getDates().get(0);
                    for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                        newString += ", " + leaveofAbsenceRequest.getDates().get(i);
                    }
                    newString += " at " + notification.getRequestDate() + ".\nTA " + ta.getName() + "'s Message:\n" + leaveofAbsenceRequest.getMessage();
                    output.add(newString);
                }
                else if (notification.getStatus() == 1){
                    if (leaveofAbsenceRequest.getRespondentID() == chairID){
                        TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
                        String newString = "You have approved the Leave of Absence Request requested by TA " + ta.getName() +
                        " for dates " + leaveofAbsenceRequest.getDates().get(0);
                        for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                            newString += ", " + leaveofAbsenceRequest.getDates().get(i);
                        }
                        newString += ".\nThe TA " + ta.getName() + "'s Message was:\n" + leaveofAbsenceRequest.getMessage();
                        output.add(newString);
                    }
                    else {
                        TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
                        DepartmentChair departmentChair = departmentChairService.getDepartmentChairById(leaveofAbsenceRequest.getRespondentID());
                        String newString = "The Department Chair " +  departmentChair.getName() + " has approved the Leave of Absence Request requested by TA " + 
                        ta.getName() + " for dates " + leaveofAbsenceRequest.getDates().get(0);
                        for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                            newString += ", " + leaveofAbsenceRequest.getDates().get(i);
                        }
                        newString += ".\nThe TA " + ta.getName() + "'s Message was:\n" + leaveofAbsenceRequest.getMessage();
                        output.add(newString);
                    }
                }
                else {
                    if (leaveofAbsenceRequest.getRespondentID() == chairID){
                        TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
                        String newString = "You have rejected the Leave of Absence Request requested by TA " + ta.getName() +
                        " for dates " + leaveofAbsenceRequest.getDates().get(0);
                        for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                            newString += ", " + leaveofAbsenceRequest.getDates().get(i);
                        }
                        newString += ".\nThe TA " + ta.getName() + "'s Message was:\n" + leaveofAbsenceRequest.getMessage();
                        output.add(newString);
                    }
                    else {
                        TA ta = taService.getTAByID(leaveofAbsenceRequest.getOwnerID());
                        DepartmentChair departmentChair = departmentChairService.getDepartmentChairById(leaveofAbsenceRequest.getRespondentID());
                        String newString = "The Department Chair " +  departmentChair.getName() + " has rejected the Leave of Absence Request requested by TA " + 
                        ta.getName() + " for dates " + leaveofAbsenceRequest.getDates().get(0);
                        for (int i = 1; i < leaveofAbsenceRequest.getDates().size(); i++){
                            newString += ", " + leaveofAbsenceRequest.getDates().get(i);
                        }
                        newString += ".\nThe TA " + ta.getName() + "'s Message was:\n" + leaveofAbsenceRequest.getMessage();
                        output.add(newString);
                    }
                }
            }
        }
        return output;
    }
    
    public List<String> viewNotificationsDepartmentStaff(int id){
        ArrayList<String> output = new ArrayList<>();
        List<Notification> notifications = getAllNotifications();
        for (Notification notification : notifications){
            Request request = requestRepository.findById(notification.getRequestId()).orElse(null);
            if (request != null){
                if (request.getRequestType() == RequestTypes.AUTOMATIC_SWAP_REQUEST){
                    AutomaticSwapRequest automaticSwapRequest = (AutomaticSwapRequest) request;
                    TA taFirst = taService.getTAByID(automaticSwapRequest.getFirstTAID());
                    TA taSecond = taService.getTAByID(automaticSwapRequest.getSecondTAID());
                    ProctoringAssignment proctoringAssignmentFirst = proctoringAssignmentService.getProctoringAssignmentByID(automaticSwapRequest.getFirstTAsProctoringAssignmentID());
                    ProctoringAssignment proctoringAssignmentSecond = proctoringAssignmentService.getProctoringAssignmentByID(automaticSwapRequest.getSecondTAsProctoringAssignmentID());
                    DepartmentStaff departmentStaff = departmentStaffService.getDepartmentStaffById(id);
                    if (taFirst == null || taSecond == null || proctoringAssignmentFirst == null || proctoringAssignmentSecond == null || departmentStaff == null){
                        continue;
                    }
                    String newString = "";
                    if (id == automaticSwapRequest.getOwnerID()){
                        newString += "You have initialized a Proctoring Swap between TA " + taFirst.getName() + " and TA " + taSecond.getName() +
                        " at " + notification.getRequestDate() + ". The first TA's Proctoring Assignment (see below for more detail) and the second TA's Proctoring Assignment (see below for more detail) has been swapped.\nYour Message:\n" +
                        automaticSwapRequest.getMessage() + "\nFirst TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentFirst) +
                        "\nSecond TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentSecond);
                    }
                    else {
                        newString += "The Department Staff " + departmentStaffService.getDepartmentStaffById(id).getName() + "initialized a Proctoring Swap between TA " + taFirst.getName() + " and TA " + taSecond.getName() +
                        " at " + notification.getRequestDate() + ". The first TA's Proctoring Assignment (see below for more detail) and the second TA's Proctoring Assignment (see below for more detail) has been swapped.\nFirst TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentFirst) +
                        "\nSecond TA's Proctoring Assignment Information:\n" + convertProctoringAssignmentToString(proctoringAssignmentSecond);
                    }
                    output.add(newString);
                }
            }
        }
        return output;
    }

    public List<String> viewNotificationsInstructor(int id){
        ArrayList<String> output = new ArrayList<>();
        List<Notification> notifications = getAllNotifications();
        for (Notification notification : notifications){
            Request request = requestRepository.findById(notification.getRequestId()).orElse(null);
            if (request != null) {
                if (request.getRequestType() == RequestTypes.TASK_SUBMISSION_REQUEST){
                    TaskSubmissionRequest taskSubmissionRequest = (TaskSubmissionRequest) request;
                    Course course = coursesService.getCourseByID(taskSubmissionRequest.getCourseID());
                    if (course != null && course.getInstructorID() == id){
                        TA ta = taService.getTAByID(taskSubmissionRequest.getOwnerID());
                        if (ta == null){
                            continue;
                        }
                        String newString = "";
                        if (notification.getStatus() == 0){
                            newString += "TA " + ta.getName() + "has sent you a Task Submission Request at " + notification.getRequestDate() + ".";
                        }
                        else if (notification.getStatus() == 1){
                            newString += "You have approved the Task Submission Request sent to you by TA " + ta.getName() + " at " + notification.getRequestDate() + ".";
                        }
                        else {
                            newString += "You have rejected the Task Submission Request sent to you by TA " + ta.getName() + " at " + notification.getRequestDate() + ".";
                        }
                        newString += "\nThe Sender TA Information:\n" + "TA Name : " + ta.getName() + "\nTA ID: " + ta.getUsername() +
                        "\nTA Current Total Workload: " + ta.getTotalWorkload() + "TA " + ta.getName();
                        if (notification.getStatus() == 0){
                            newString += "'s Message:\n";
                        }
                        else {
                            newString += "'s Message was:\n";
                        } 
                        newString += taskSubmissionRequest.getMessage();
                        output.add(newString);
                    }
                }
            }
        }
        return output;
    }

    private String convertProctoringAssignmentToString(ProctoringAssignment proctoringAssignment){
        if (proctoringAssignment == null){
            return "No Proctoring Assignment";
        }
        String newElement = "Course Name: " + coursesService.getCourseByID(proctoringAssignment.getCourseID()).getCode() + 
        "\nExam Place: " + proctoringAssignment.getExamPlace() + "\nExam Date: " + proctoringAssignment.getDay() + "/" + 
        proctoringAssignment.getMonth() + "/" + proctoringAssignment.getDay() + "\nExam Time: " + 
        proctoringAssignment.getStartTime() + "-" + proctoringAssignment.getEndTime();
        return newElement;
    }
}
