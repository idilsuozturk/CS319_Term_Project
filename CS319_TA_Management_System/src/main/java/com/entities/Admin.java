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
public class Admin extends User {
  

    @Enumerated(EnumType.STRING)
    private Roles role;

    public Admin() {
        super();
        this.role = Roles.ADMIN;
       
    }
    
    public Admin( String username, String name, String email, String password) {
        super(name, email, username, password, Roles.ADMIN);
        this.role = Roles.ADMIN;
    }

    public Integer getId() {
        return super.getId();
    }
    public Roles getRole() {
        return role;
    }
    public void setUsername(String username) {
        super.setUsername(username);
    }

    public String getUsername() {
        return super.getUsername();
    }
    
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
       super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }




}
