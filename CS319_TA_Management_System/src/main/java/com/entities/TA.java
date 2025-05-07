package com.entities;

import com.converters.*;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name = "tas")
public class TA extends User {
    Integer advisorID; // instructor ID number

    private Integer totalWorkload;

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<Integer> coursesAssisted;

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<Integer> coursesTaken;

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<Integer> proctoringAssignmentIDs;

    @Column(columnDefinition = "json")
    @Convert(converter = StringArrayToJsonConverter.class)
    private String[] schedule;
    

    public TA() {
        super();
        this.advisorID = -1;
        this.totalWorkload = -1;
        this.coursesAssisted = null;
        this.coursesTaken = null;
        this.proctoringAssignmentIDs = null;
        this.schedule = null;
    }
    
    public TA(String name, String email, String username, String password, Integer advisorID) {
        super(name, email, username, password, Roles.TA);
        this.advisorID = advisorID;
        this.totalWorkload = 0;
        this.coursesAssisted = new ArrayList<Integer>();
        this.coursesTaken = new ArrayList<Integer>();
        this.proctoringAssignmentIDs = new ArrayList<Integer>();
        this.schedule = new String[98];
    }

    public ArrayList<Integer> getCoursesAssisted() {
        return this.coursesAssisted;
    }

    public void setCoursesAssisted(ArrayList<Integer> coursesAssisted) {
        this.coursesAssisted = coursesAssisted;
    }

    public ArrayList<Integer> getCoursesTaken() {
        return this.coursesTaken;
    }

    public void setCoursesTaken(ArrayList<Integer> coursesTaken) {
        this.coursesTaken = coursesTaken;
    }

    public Integer getAdvisorID() {
        return this.advisorID;
    }

    public void setAdvisorID(Integer advisorID) {
        this.advisorID = advisorID;
    }

    public Integer getTotalWorkload() {
        return this.totalWorkload;
    }

    public void setTotalWorkload(Integer totalWorkload) {
        this.totalWorkload = totalWorkload;
    }

    public ArrayList<Integer> getProctoringAssignmentIDs() {
        return this.proctoringAssignmentIDs;
    }

    public void setProctoringAssignmentIDs(ArrayList<Integer> proctoringAssignmentIDs) {
        this.proctoringAssignmentIDs = proctoringAssignmentIDs;
    }

    public String[] getSchedule(){
        return this.schedule;
    }

    public void setSchedule(String[] schedule){
        this.schedule = schedule;
    }
}