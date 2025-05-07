package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    private String taskDate;
    private Integer taskDuration;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String description;

    @ManyToOne
    @JoinColumn(name = "ta_id")
    private TA ta;

    // Default 
    public Task() {
        this.taskId = null;
        this.taskDate = null;
        this.taskDuration = 0;

        this.course = null;
        this.description = null;
        this.ta = null;
    }

   
    public Task(Integer taskId, String taskDate, Integer taskDuration,

            Course course,
             String description, TA ta) {
        this.taskId = taskId;
        this.taskDate = taskDate;


        this.taskDuration = taskDuration;
        this.course = course;

        this.description = description;
        this.ta = ta;
    }

    // Get and Set
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public Integer getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(Integer taskDuration) {
        this.taskDuration = taskDuration;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TA getTa() {
        return ta;
    }

    public void setTa(TA ta) {
        this.ta = ta;
    }
}