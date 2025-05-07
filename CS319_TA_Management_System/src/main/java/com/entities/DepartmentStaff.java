package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "department_staff")
public class DepartmentStaff extends Staff {

    public DepartmentStaff() {
        super();
        setRole(Roles.DEPARTMENT_STAFF);
    }
    public DepartmentStaff(String name, String email, String username, String password, String departmentCode) {
        super(name, email, username, password, departmentCode, Roles.DEPARTMENT_STAFF);
    }
}
