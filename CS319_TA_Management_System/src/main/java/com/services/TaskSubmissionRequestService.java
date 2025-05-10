package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Course;
import com.entities.Instructor;
import com.entities.Notification;
import com.entities.TA;
import com.entities.TaskSubmissionRequest;
import com.entities.TaskTypes;
import com.repositories.TaskSubmissionRequestRepository;

@Service
public class TaskSubmissionRequestService {
    private final TaskSubmissionRequestRepository taskSubmissionRequestRepository;

    private final CoursesService coursesService;

    private final InstructorService instructorService;

    private final NotificationService notificationService;

    private final TAService taService;

    public TaskSubmissionRequestService(TaskSubmissionRequestRepository taskSubmissionRequestRepository, CoursesService coursesService, InstructorService instructorService, NotificationService notificationService, TAService taService) {
        this.taskSubmissionRequestRepository = taskSubmissionRequestRepository;
        this.coursesService = coursesService;
        this.instructorService = instructorService;
        this.notificationService = notificationService;
        this.taService = taService;
    }

    public List<TaskSubmissionRequest> getAllTaskSubmissionRequests() {
        return taskSubmissionRequestRepository.findAll(); 
    }

    public TaskSubmissionRequest createTaskSubmissionRequest(int ownerID, String message, TaskTypes taskType, String taskDate, int courseID) {
        return taskSubmissionRequestRepository.save(new TaskSubmissionRequest(ownerID, message, taskType, taskDate, courseID));
    }

    public void deleteTaskSubmissionRequestByID(Integer id) {
        taskSubmissionRequestRepository.deleteById(id);  
    }

    public TaskSubmissionRequest updateTaskSubmissionRequestByID(Integer id, TaskSubmissionRequest taskSubmissionRequest) {
        TaskSubmissionRequest existingTaskSubmissionRequest = taskSubmissionRequestRepository.findById(id).orElse(null);  
        if (existingTaskSubmissionRequest != null) {
            existingTaskSubmissionRequest.setRequestType(taskSubmissionRequest.getRequestType());
            existingTaskSubmissionRequest.setMessage(taskSubmissionRequest.getMessage());
            existingTaskSubmissionRequest.setOwnerID(taskSubmissionRequest.getOwnerID());
            existingTaskSubmissionRequest.setPending(taskSubmissionRequest.getPending());
            existingTaskSubmissionRequest.setTaskType(taskSubmissionRequest.getTaskType());
            existingTaskSubmissionRequest.setTaskDate(taskSubmissionRequest.getTaskDate());
            existingTaskSubmissionRequest.setCourseID(taskSubmissionRequest.getCourseID());
            return taskSubmissionRequestRepository.save(existingTaskSubmissionRequest);  
        }
        return null;  
    }

    public TaskSubmissionRequest getTaskSubmissionRequestByID(Integer id) {
        return taskSubmissionRequestRepository.findById(id).orElse(null);  
    }

    public List<TaskSubmissionRequest> viewRequests(int id){
        List<TaskSubmissionRequest> taskSubmissionRequests = getAllTaskSubmissionRequests();
        ArrayList<TaskSubmissionRequest> output = new ArrayList<>();
        for (TaskSubmissionRequest taskSubmissionRequest : taskSubmissionRequests){
            Course course = coursesService.getCourseByID(taskSubmissionRequest.getCourseID());
            if (course == null) continue;
            if (course.getInstructorID() == id){
                if (taskSubmissionRequest.getPending()){
                    output.add(taskSubmissionRequest);
                }
            }
        }
        return output;
    }

    public void createTaskSubmissionRequest(int id, TaskTypes taskType, String taskDate, String requestDate, String message, int courseID){
        TaskSubmissionRequest taskSubmissionRequest = createTaskSubmissionRequest(id, message, taskType, taskDate, courseID);
        notificationService.createNotification(requestDate, taskSubmissionRequest.getID(), 0);
    }

    public boolean approveTaskSubmissionRequest(String approvalDate, int instructorID, int taskSubmissionID){
        TaskSubmissionRequest taskSubmissionRequest = getTaskSubmissionRequestByID(taskSubmissionID);
        TA ta = taService.getTAByID(taskSubmissionRequest.getOwnerID());
        if (ta == null || taskSubmissionRequest == null) return false;
        if (taskSubmissionRequest.getTaskType() == TaskTypes.GRADING){
            int newTotalWorkload = ta.getTotalWorkload();
            newTotalWorkload += 3;
            ta.setTotalWorkload(newTotalWorkload);
        }
        else if (taskSubmissionRequest.getTaskType() == TaskTypes.LAB){
            int newTotalWorkload = ta.getTotalWorkload();
            newTotalWorkload += 1 + taskSubmissionRequest.getDuration();
            ta.setTotalWorkload(newTotalWorkload);
        }
        else if (taskSubmissionRequest.getTaskType() == TaskTypes.OFFICE_HOUR){
            int newTotalWorkload = ta.getTotalWorkload();
            newTotalWorkload += 2;
            ta.setTotalWorkload(newTotalWorkload);
        }
        else if (taskSubmissionRequest.getTaskType() == TaskTypes.PROCTORING){
            int newTotalWorkload = ta.getTotalWorkload();
            newTotalWorkload += 3;
            ta.setTotalWorkload(newTotalWorkload);
        }
        else {
            int newTotalWorkload = ta.getTotalWorkload();
            newTotalWorkload += 4;
            ta.setTotalWorkload(newTotalWorkload);
        }
        taskSubmissionRequest.setPending(false);
        taskSubmissionRequest.setRespondentID(instructorID);
        notificationService.createNotification(approvalDate, taskSubmissionID, 1);
        taService.updateTAByID(ta.getId(), ta);
        updateTaskSubmissionRequestByID(taskSubmissionID, taskSubmissionRequest);
        return true;
    }

    public boolean rejectTaskSubmissionRequest(String rejectionDate, int instructorID, int taskSubmissionID){
        TaskSubmissionRequest taskSubmissionRequest = getTaskSubmissionRequestByID(taskSubmissionID);
        taskSubmissionRequest.setPending(false);
        taskSubmissionRequest.setRespondentID(instructorID);
        notificationService.createNotification(rejectionDate, taskSubmissionID, 2);
        updateTaskSubmissionRequestByID(taskSubmissionID, taskSubmissionRequest);
        return true;
    }

    public List<String> viewTaskHistory(int id){
        List<TaskSubmissionRequest> requests = getAllTaskSubmissionRequests();
        ArrayList<String> output = new ArrayList<>();
        for (TaskSubmissionRequest request : requests){
            if (!request.getPending()){
                if (request.getOwnerID() == id){
                    output.add(taskSubmissionRequestToString(request));
                }
            }
        }
        return output;
    }

    private String taskSubmissionRequestToString(TaskSubmissionRequest request) {
        Notification notification = notificationService.getNotificationByRequestIDAndStatusNot(null, 0);
        Instructor instructor = instructorService.getInstructorByID(request.getRespondentID());
        Course course = coursesService.getCourseByID(request.getCourseID());
        if (notification == null || instructor == null) return null;
        String output = "";
        if (notification.getStatus() == 1){
            output += "Your Task Submission was approved by Instructor " + instructor.getName() + " at " + notification.getRequestDate() + ".";
        }
        else {
            output += "Your Task Submission was rejected by Instructor " + instructor.getName() + " at " + notification.getRequestDate() + ".";
        }
        output += "\nTask Information:\n" + "Task Type: " ;
        if (request.getTaskType() == TaskTypes.GRADING){
            output += "Grading\nDuration: " + request.getDuration();
        }
        else if (request.getTaskType() == TaskTypes.LAB){
            output += "Lab\nDuration: " + request.getDuration() + "\nTask Date: " + request.getTaskDate();
        }
        else if (request.getTaskType() == TaskTypes.OFFICE_HOUR){
            output += "Office Hour" + "\nTask Date: " + request.getTaskDate();
        }
        else if (request.getTaskType() == TaskTypes.PROCTORING){
            output += "Proctoring" + "\nTask Date: " + request.getTaskDate();
        }
        else {
            output += "Recitation" + "\nTask Date: " + request.getTaskDate();
        }
        output += "\nCourse Name: " + course.getCode();
        output += "\nYour Message was: " + request.getMessage();
        return output;
    }
}
