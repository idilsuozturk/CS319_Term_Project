package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "department_staff")
public class DepartmentStaff extends Staff {

    public DepartmentStaff() {
        super();
        setRole(Roles.DEPARTMENT_STAFF);
    }
    public DepartmentStaff(String firstName, String lastName, String email, String username, String password, String departmentCode) {
        super(firstName, lastName, email, username, password, departmentCode, Roles.DEPARTMENT_STAFF);
    }
}
