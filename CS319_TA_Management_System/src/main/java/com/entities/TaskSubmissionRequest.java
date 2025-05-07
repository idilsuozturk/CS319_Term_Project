package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tasksubmissionrequests")
public class TaskSubmissionRequest extends Request {
    String taskType;
    String taskDate;

    public TaskSubmissionRequest(){
        super();
        setRequestType(RequestTypes.TASK_SUBMISSION_REQUEST);
        this.taskType = null;
        this.taskDate = null;
    }

    public TaskSubmissionRequest(String requestDate, int ownerID, String message, String taskType, String taskDate){
        super(requestDate, RequestTypes.TASK_SUBMISSION_REQUEST, ownerID, message);
        this.taskType = taskType;
        this.taskDate = taskDate;
    }

    public String getTaskType(){
        return this.taskType;
    }

    public void setTaskType(String taskType){
        this.taskType = taskType;
    }

    public String getTaskDate(){
        return this.taskDate;
    }

    public void setTaskDate(String taskDate){
        this.taskDate = taskDate;
    }
}
