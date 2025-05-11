package com.entities;

import java.util.ArrayList;

import com.converters.IntegerArrayListToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructors")
public class Instructor extends Staff {
    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayListToJsonConverter.class)
    private ArrayList<Integer> taIDs;

    public Instructor() {
        super();
        this.taIDs = null;
    }

    public Instructor(String firstName, String lastName, String email, String username, String password, String departmentCode, ArrayList<Integer> taIDs) {
        super(firstName, lastName, email, username, password, departmentCode, Roles.INSTRUCTOR);
        this.taIDs = taIDs;
    }

    public ArrayList<Integer> getTaIDs() {
        return this.taIDs;
    }

    public void setTaIDs(ArrayList<Integer> taIDs) {
        this.taIDs = taIDs;
    }
}
