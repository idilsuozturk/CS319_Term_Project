package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "instructor")
public class Instructor extends Staff {

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer[] courses;

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer[] tas;
    private String tcNumber;

    public Instructor() {
        super();
        this.courses = null;
        this.tas = null;
        //this.departmentCode = "";
        this.tcNumber = "";
    }

    public Instructor(String name, String email, String userName, String password, Integer[] courses, Integer[] tas, String departmentCode, String tcNumber) {
        super(name, email, userName, password, departmentCode, "Instructor");
        super.setRole(Roles.INSTRUCTOR);
        this.courses = courses;
        this.tas = tas;
        this.tcNumber = tcNumber;

        
    }

    // Get and set functions

    public Integer getId() {
        return super.getId();
    }

    public Integer[] getCourses() {
        return this.courses;
    }

    public void setCourses(Integer[] courses) {
        this.courses = courses;
    }

    public Integer[] getTas() {
        return this.tas;
    }

    public void setTas(Integer[] tas) {
        this.tas = tas;
    }

    public String getTcNumber() {
        return this.tcNumber;
    }

    public void setTcNumber(String tcNumber) {
        this.tcNumber = tcNumber;
    }
}