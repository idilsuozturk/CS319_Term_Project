package com.entities;

import jakarta.persistence.*;

@Entity
public class DepartmentStaff extends Staff {
    private String tcNumber;

    public DepartmentStaff() {
        super();
        this.tcNumber = "";
    }
    public DepartmentStaff(Integer id, String email, String userName, String password, String departmentCode, String tcNumber) {
        super(id, email, userName, password, departmentCode, "Department Staff");
        this.tcNumber = tcNumber;
    }
    public String getTcNumber() {
        return this.tcNumber;
    }

    public void setTcNumber(String tcNumber) {
        this.tcNumber = tcNumber;
    }
}