package com.entities;

import com.converters.IntegerArrayListToJsonConverter;

import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table(name = "instructors")
public class Instructor extends Staff {

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<Integer> courseIDs;

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<Integer> taIDs;

    public Instructor() {
        super();
        this.courseIDs = null;
        this.taIDs = null;
    }

    public Instructor(String name, String email, String username, String password, String departmentCode, ArrayList<Integer> courseIDs, ArrayList<Integer> taIDs) {
        super(name, email, username, password, departmentCode, Roles.INSTRUCTOR);
        this.courseIDs = courseIDs;
        this.taIDs = taIDs;
    }

    public ArrayList<Integer> getCourseIDs() {
        return this.courseIDs;
    }

    public void setCourseIDs(ArrayList<Integer> courseIDs) {
        this.courseIDs = courseIDs;
    }

    public ArrayList<Integer> getTaIDs() {
        return this.taIDs;
    }

    public void setTaIDs(ArrayList<Integer> taIDs) {
        this.taIDs = taIDs;
    }
}
