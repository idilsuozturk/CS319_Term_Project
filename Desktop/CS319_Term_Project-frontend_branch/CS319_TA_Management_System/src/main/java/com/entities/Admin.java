package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;


@Entity
@Table(name = "admins")
public class Admin extends User {
  
    //private String username;
   // private String admin;

    public Admin() {
        super();
        super.setRole(Roles.ADMIN);
        //this.username = null;
        //this.admin = null;
       
    }
    
    public Admin( String username, String name, String email, String password) {
        super(name, email, username, password, Roles.ADMIN);
        //this.username = username;
        //this.admin = username;
  
    }

    /*public Integer getId() {
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
    }*/

}
