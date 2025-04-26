package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "department_staff")
public class DepartmentStaff extends Staff {
    private String tcNumber;

    public DepartmentStaff() {
        super();
        this.tcNumber = "";
    }
    public DepartmentStaff(String name, String email, String userName, String password, String departmentCode, String tcNumber) {
        super(name, email, userName, password, departmentCode, "Department Staff");
        super.setRole(Roles.DEPARTMENT_STAFF);
        this.tcNumber = tcNumber;
    }
    public String getTcNumber() {
        return this.tcNumber;
    }

    public void setTcNumber(String tcNumber) {
        this.tcNumber = tcNumber;
    }
}