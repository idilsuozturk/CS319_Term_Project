package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public class Staff extends User {
    private String departmentCode;

    public Staff() {
        super();
        this.departmentCode = null;
    }
    public Staff( String name, String email, String username, String password, String departmentCode, Roles role) {
        super( name, email, username, password, role);
        this.departmentCode = departmentCode;
    }


    public Integer getId() {
        return super.getId();
    }

    public void setRole(Roles role) {
        super.setRole(role);
    }
    
    public String getDepartmentCode() {
        return this.departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
