package com.entities;

import javax.management.relation.Role;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String name;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;
    
    public User( String email, String userName, String password, Roles role) {
        this.email = email;
        this.username = userName;
        this.password = password;
        this.role = role;
    }

    public User() {
        //this.id = GeneratedValue.class.getAnnotation(Generated.class).value();
        email = null;
        username = null;
        password = null;
        role = Roles.UNKNOWN;
    }

    public Integer getId() {
        return id;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
