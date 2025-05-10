package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    
    public Admin(String username, String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, username, password, Roles.ADMIN);
        this.role = Roles.ADMIN;
    }
}
