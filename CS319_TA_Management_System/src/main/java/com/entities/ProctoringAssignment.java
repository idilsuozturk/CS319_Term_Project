package com.entities;

import jakarta.persistence.*;
@Entity
public class ProctoringAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private int year;
    private int month;
    private int day;
    private int startDate;
    private int endDate;
    private String examPlace;
    private int courseID;
    private int proctorID;

    public ProctoringAssignment(){
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.startDate = -1;
        this.endDate = -1;
        this.examPlace = null;
        this.courseID = -1;
        this.proctorID = -1;
    }

    public ProctoringAssignment(int year, int month, int day, int startDate, int endDate, String examPlace, int courseID, int proctorID){
        this.year = year;
        this.month = month;
        this.day = day;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examPlace = examPlace;
        this.courseID = courseID;
        this.proctorID = proctorID;
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

    public int getStartDate(){
        return this.startDate;
    }

    public void setStartDate(int startDate){
        this.startDate = startDate;
    }

    public int getEndDate(){
        return this.endDate;
    }

    public void setEndDate(int endDate){
        this.endDate = endDate;
    }

    public String getExamPlace() {
        return this.examPlace;
    }

    public void setExamPlace(String examPlace){
        this.examPlace = examPlace;
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
