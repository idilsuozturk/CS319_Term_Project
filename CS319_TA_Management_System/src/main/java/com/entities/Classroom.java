package com.entities;

import com.converters.StringArrayToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String classroomName;
    private int capacity;
    private int examCapacity;
    @Column(columnDefinition = "json")
    @Convert(converter = StringArrayToJsonConverter.class)
    private String[] examList;

    public Classroom (){
        this.classroomName = null;
        this.capacity = -1;
        this.examCapacity = -1;
        this.examList = null;
    }

    public Classroom(String classroomName, int capacity, int examCapacity, String[] examList){
        this.classroomName = classroomName;
        this.capacity = capacity;
        this.examCapacity = examCapacity;
        this.examList = examList;
    }

    public int getID(){
        return this.id;
    }

    public String getClassroomName(){
        return this.classroomName;
    }

    public void setClassroomName(String classroomName){
        this.classroomName = classroomName;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getExamCapacity(){
        return this.examCapacity;
    }

    public void setExamCapacity(int examCapacity){
        this.examCapacity = examCapacity;
    }

    public String[] getExamList(){
        return examList;
    }

    public void setExamList(String[] examList){
        this.examList = examList;
    }
}
