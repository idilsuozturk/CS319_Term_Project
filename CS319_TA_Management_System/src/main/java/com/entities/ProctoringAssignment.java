package com.entities;

import com.converters.StringArrayToJsonConverter;

import jakarta.persistence.*;
@Entity
public class ProctoringAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private int year;
    private int month;
    private int day;
    private int startTime;
    private int endTime;
    private int classroomID;
    private int courseID;
    private int proctorID;

    public ProctoringAssignment(){
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.startTime = -1;
        this.endTime = -1;
        this.classroomID = -1;
        this.courseID = -1;
        this.proctorID = -1;
    }

    public ProctoringAssignment(int year, int month, int day, int startTime, int endTime, int classroomID, int courseID, int proctorID){
        this.year = year;
        this.month = month;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomID = classroomID;
        this.courseID = courseID;
        this.proctorID = proctorID;
    }

    public int getID(){
        return id;
    }
    
    public int getYear(){
        return this.year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public int getMonth(){
        return this.month;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public int getDay(){
        return this.day;
    }

    public void setDay(int day){
        this.day = day;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public int getEndTime(){
        return this.endTime;
    }

    public void setEndTime(int endTime){
        this.endTime = endTime;
    }

    public int getClassroomID(){
        return this.classroomID;
    }

    public void setClassroomID(int classroomID){
        this.classroomID = classroomID;
    }

    public int getCourseID(){
        return this.courseID;
    }

    public void setCourseID(int courseID){
        this.courseID = courseID;
    }

    
    public int getProctorID(){
        return this.proctorID;
    }

    public void setProctorID(int proctorID){
        this.proctorID = proctorID;
    }
}
