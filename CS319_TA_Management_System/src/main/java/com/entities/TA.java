package com.entities;

import com.converters.*;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name = "tas")
public class TA extends User {
    Integer advisorID; 

    private boolean master;
    private int mode;
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

    @Column(columnDefinition = "json")
    @Convert(converter = StringArrayListToJsonConverter.class)
    private ArrayList<String> onLeaveDates;
    

    public TA() {
        super();
        this.master = false;
        this.mode = 1;
        this.advisorID = -1;
        this.totalWorkload = -1;
        this.coursesAssisted = null;
        this.coursesTaken = null;
        this.proctoringAssignmentIDs = null;
        this.schedule = null;
        this.onLeaveDates = null;
    }
    
    public TA(String name, String email, String username, String password, boolean master, Integer advisorID) {
        super(name, email, username, password, Roles.TA);
        this.master = master;
        this.mode = 1;
        this.advisorID = advisorID;
        this.totalWorkload = 0;
        this.coursesAssisted = new ArrayList<Integer>();
        this.coursesTaken = new ArrayList<Integer>();
        this.proctoringAssignmentIDs = new ArrayList<Integer>();
        this.schedule = new String[98];
        this.onLeaveDates = new ArrayList<>();

    }

    public boolean getMaster(){
        return this.master;
    }

    public void setMaster(boolean master){
        this.master = master;
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

    public int getMode(){
        return this.mode;
    }

    public void setMode(byte mode){
        this.mode = mode;
    }

    public ArrayList<String> getOnLeaveDates(){
        return this.onLeaveDates;
    }

    public void setOnLeaveDates(ArrayList<String> onLeaveDates){
        this.onLeaveDates = onLeaveDates;
    }
}
