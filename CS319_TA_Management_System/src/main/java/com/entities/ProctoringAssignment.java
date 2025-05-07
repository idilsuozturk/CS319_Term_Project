package com.entities;

import jakarta.persistence.*;
@Entity
public class ProctoringAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private short year;
    private byte month;
    private byte day;
    private short startDate;
    private short endDate;
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

    public ProctoringAssignment(short year, byte month, byte day, short startDate, short endDate, String examPlace, int courseID, int proctorID){
        this.year = year;
        this.month = month;
        this.day = day;
        this.startDate = startDate;
        this.endDate = endDate;
        this.examPlace = examPlace;
        this.courseID = courseID;
        this.proctorID = proctorID;
    }

    public short getYear(){
        return this.year;
    }

    public void setYear(short year){
        this.year = year;
    }

    public byte getMonth(){
        return this.month;
    }

    public void setMonth(byte month){
        this.month = month;
    }

    public byte getDay(){
        return this.day;
    }

    public void setDay(byte day){
        this.day = day;
    }

    public short getStartDate(){
        return this.startDate;
    }

    public void setStartDate(short startDate){
        this.startDate = startDate;
    }

    public short getEndDate(){
        return this.endDate;
    }

    public void setEndDate(short endDate){
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
