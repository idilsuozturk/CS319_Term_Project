package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public abstract class Staff extends User {
    private String departmentCode;
    private String title;

    public Staff() {
        super();
        this.departmentCode = "";
        this.title = "";
    }
    public Staff( String email, String userName, String password, String departmentCode, String title) {
        super( email, userName, password);
        this.departmentCode = departmentCode;
        this.title = title;
    }


    public Integer getId() {
        return super.getId();
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