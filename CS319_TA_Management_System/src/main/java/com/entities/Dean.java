package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "deans")
public class Dean extends Staff{


    public Dean() {
        super();
        super.setRole(Roles.DEAN);
      
    }

    public Dean(String name, String email, String userName, String password, String departmentCode, String title) {
        super(name, email, userName, password, departmentCode, title);
        super.setRole(Roles.DEAN);
    }
}