package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.TaskSubmissionRequest;
import com.repositories.TaskSubmissionRequestRepository;

@Service
public class TaskSubmissionRequestService {
    private final TaskSubmissionRequestRepository taskSubmissionRequestRepository;

    public TaskSubmissionRequestService(TaskSubmissionRequestRepository taskSubmissionRequestRepository) {
        this.taskSubmissionRequestRepository = taskSubmissionRequestRepository;
    }

    public List<TaskSubmissionRequest> getAllTaskSubmissionRequests() {
        return taskSubmissionRequestRepository.findAll(); 
    }

    public TaskSubmissionRequest createTaskSubmissionRequest(int ownerID, String message, String taskType, String taskDate) {
        return taskSubmissionRequestRepository.save(new TaskSubmissionRequest(ownerID, message, taskType, taskDate));
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
            return taskSubmissionRequestRepository.save(existingTaskSubmissionRequest);  
        }
        return null;  
    }

    public TaskSubmissionRequest getTaskSubmissionRequestByID(Integer id) {
        return taskSubmissionRequestRepository.findById(id).orElse(null);  
    }
}
