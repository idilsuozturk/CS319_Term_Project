package com.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    //Is including this attribute necessary?
    private String tcNumber;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Admin() {
        this.tcNumber = 0;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public Admin(String tcNumber, String name, String surname, String email, String password) {
        this.tcNumber = "";
        this.name = "";
        this.surname = "";
        this.email = "";
        this.password = "";
    }

    // Get and set functions

    public String getTcNumber() {
        return tcNumber;
    }

    public void setCurrentAssistingCourses(String tcNumber) {
        this.tcNumber = tcNumber;
    }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}