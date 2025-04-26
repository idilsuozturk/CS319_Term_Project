package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public class Staff extends User {
    private String departmentCode;
    private String title;

    public Staff() {
        super();
        this.departmentCode = "";
        this.title = "";
    }
    public Staff( String name, String email, String userName, String password, String departmentCode, String title) {
        super( name, email, userName, password, Roles.UNKNOWN);
        this.departmentCode = departmentCode;
        this.title = title;
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

    public String getTitle() {
        return this.departmentCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}