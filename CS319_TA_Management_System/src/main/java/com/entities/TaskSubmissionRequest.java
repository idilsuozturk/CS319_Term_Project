package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "task_submission_requests")
public class TaskSubmissionRequest extends Request {
    private TaskTypes taskType;
    private String taskDate;
    private int courseID;
    private int duration;
    private int respondentID;

    public TaskSubmissionRequest(){
        super();
        setRequestType(RequestTypes.TASK_SUBMISSION_REQUEST);
        this.taskType = TaskTypes.UNKNOWN;
        this.taskDate = null;
        this.courseID = -1;
        this.duration = 0;
        this.respondentID = -1;
    }

    public TaskSubmissionRequest(int ownerID, String message, TaskTypes taskType, String taskDate, int courseID){
        super(RequestTypes.TASK_SUBMISSION_REQUEST, ownerID, message);
        this.taskType = taskType;
        this.taskDate = taskDate;
        this.courseID = courseID;
        this.duration = 0;
        this.respondentID = 0;
    }

    public TaskTypes getTaskType(){
        return this.taskType;
    }

    public void setTaskType(TaskTypes taskType){
        this.taskType = taskType;
    }

    public String getTaskDate(){
        return this.taskDate;
    }

    public void setTaskDate(String taskDate){
        this.taskDate = taskDate;
    }

    public int getCourseID(){
        return this.courseID;
    }

    public void setCourseID(int courseID){
        this.courseID = courseID;
    }

    public int getDuration(){
        return this.duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public int getRespondentID(){
        return this.respondentID;
    }

    public void setRespondentID(int respondentID){
        this.respondentID = respondentID;
    }
}
