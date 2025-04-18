package com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
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
    private String courseName;
    private Integer section;

    private Integer instructor;
    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer [] students;
    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer [] TAs;

    //@Embedded
    //private Schedule schedule;

    public Course () {
        // Default constructor
        id = -1;
        courseName = null;  
        section = 0;
        instructor = 0;
        students = null;
        TAs = null;
    }


    public Course( 
        String courseName, 
        Integer section, 
        Integer instructor, 
        Integer [] students, 
        Integer [] TAs 
        //Schedule schedule
        ) {
        this.courseName = courseName;
        this.section = section;
        this.instructor = instructor;
        this.students = students;
        this.TAs = TAs;
        //this.schedule = schedule;
    }

    public Integer getId() {
        return id;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getInstructor() {
        return instructor;
    }

    public void setInstructor(Integer instructor) {
        this.instructor = instructor;
    } 

    public Integer[] getStudents() {
        return students;
    }

    public void setStudents(Integer[] students) {
        this.students = students;
    }

    public Integer[] getTAs() {
        return TAs;
    }

    public void setTAs(Integer[] tAs) {
        TAs = tAs;
    }
    /*
    public Schedule getSchedule() {
        return schedule;
    }
    
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }*/
}
   
