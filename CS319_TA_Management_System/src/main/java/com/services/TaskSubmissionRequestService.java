package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.TA;
import com.entities.TaskSubmissionRequest;
import com.entities.TaskTypes;
import com.repositories.TaskSubmissionRequestRepository;

@Service
public class TaskSubmissionRequestService {
    private final TaskSubmissionRequestRepository taskSubmissionRequestRepository;

    private final CoursesService coursesService;

    private final NotificationService notificationService;

    private final TAService taService;

    public TaskSubmissionRequestService(TaskSubmissionRequestRepository taskSubmissionRequestRepository, CoursesService coursesService, NotificationService notificationService, TAService taService) {
        this.taskSubmissionRequestRepository = taskSubmissionRequestRepository;
        this.coursesService = coursesService;
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
            if (coursesService.getCourseByID(taskSubmissionRequest.getCourseID()).getInstructorID() == id){
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
}
