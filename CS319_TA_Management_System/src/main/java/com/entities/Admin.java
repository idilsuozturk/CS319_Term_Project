package com.entities;

import javax.management.relation.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "admins")
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String username;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public Admin() {
        this.username = null;
        this.name = null;  
        this.email = null;
        this.password = null;
        this.role = Roles.ADMIN;
    }
    
    public Admin( String username, String name, String email, String password) {
        this.username = username;
        this.name = name;  
        this.email = email;
        this.password = password;
        this.role = Roles.ADMIN;
    }

    public Integer getId() {
        return id;
    }
    public Roles getRole() {
        return role;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
