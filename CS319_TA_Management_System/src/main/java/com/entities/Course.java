package com.entities;
import java.util.ArrayList;

import com.converters.IntegerArrayListToJsonConverter;
import com.converters.StringArrayToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String section;
    private boolean masterphd;

    private Integer instructorID;
    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<Integer> taIDs;
    @Column(columnDefinition = "json")
    @Convert(converter = StringArrayToJsonConverter.class)
    private String[] schedule;

    public Course () {
        this.code = null;  
        this.section = null;
        this.instructorID = null;
        this.taIDs = null;
        this.schedule = null;
        this.masterphd = false;
    }


    public Course(String code, String section, Integer instructorID, ArrayList<Integer> taIDs, String[] schedule, boolean masterphd) {
        this.code = code;
        this.section = section;
        this.instructorID = instructorID;
        this.taIDs = taIDs;
        this.schedule = schedule;
        this.masterphd = masterphd;
    }

    public Integer getId() {
        return this.id;
    }


    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getInstructorID() {
        return this.instructorID;
    }

    public void setInstructorID(Integer instructorID) {
        this.instructorID = instructorID;
    } 

        public ArrayList<Integer> getTaIDs() {
        return this.taIDs;
    }

    public void setTaIDs(ArrayList<Integer> taIDs) {
        this.taIDs = taIDs;
    }
    public String[] getSchedule() {
        return this.schedule;
    }
    
    public void setSchedule(String[] schedule) {
        this.schedule = schedule;
    }

    public boolean getMasterphd(){
        return this.masterphd;
    }

    public void setMasterphd(boolean masterphd){
        this.masterphd = masterphd;
    }
}
   
