package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "deans")
public class Dean extends Staff{
    
    private String tcNumber;

    public Dean() {
        super();
        super.setRole(Roles.DEAN);
        this.tcNumber = null;
    }

    public Dean(String name, String email, String userName, String password, String departmentCode, String title, String tcNumber) {
        super(name, email, userName, password, departmentCode, title);
        super.setRole(Roles.DEAN);
        this.tcNumber = tcNumber;
    }

    public String getTcNumber() {
        return tcNumber;
    }

    public void setTcNumber(String tcNumber) {
        this.tcNumber = tcNumber;
    }
}