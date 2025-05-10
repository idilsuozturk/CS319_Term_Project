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

    public Dean(String firstName, String lastName, String email, String username, String password, String departmentCode) {
        super(firstName, lastName, email, username, password, departmentCode, Roles.DEAN);
    }
}
