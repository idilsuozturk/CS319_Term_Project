package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "department_chair")

public class DepartmentChair extends Staff{


    public DepartmentChair() {
        super();
        super.setRole(Roles.DEPARTMENT_CHAIR);
    }

    public DepartmentChair(String name, String email, String userName, String password, String departmentCode, String title) {
        super(name, email, userName, password, departmentCode, title);
        super.setRole(Roles.DEPARTMENT_CHAIR);
    }

    public Integer getId() {
        return super.getId();
    }

    public void setRole(Roles role) {
        super.setRole(role);
    }

    public String getDepartmentCode() {
        return super.getDepartmentCode();
    }

    public void setDepartmentCode(String departmentCode) {
        super.setDepartmentCode(departmentCode);
    }

    public String getTitle() {
        return super.getTitle();
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }
}
