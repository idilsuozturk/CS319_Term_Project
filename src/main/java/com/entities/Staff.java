package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class Staff extends User {
    private String departmentCode;
    private String title;

    public Staff() {
        super();
        this.departmentCode = "";
        this.title = "";
    }
    public Staff(Integer id, String email, String userName, String password, String departmentCode, String title) {
        super(id, email, userName, password);
        this.departmentCode = departmentCode;
        this.tcNumber = title;
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