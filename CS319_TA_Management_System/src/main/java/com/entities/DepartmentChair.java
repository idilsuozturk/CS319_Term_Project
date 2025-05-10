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

    public DepartmentChair(String firstName, String lastName, String email, String userName, String password, String departmentCode) {
        super(firstName, lastName, email, userName, password, departmentCode, Roles.DEPARTMENT_CHAIR);
    }
}
