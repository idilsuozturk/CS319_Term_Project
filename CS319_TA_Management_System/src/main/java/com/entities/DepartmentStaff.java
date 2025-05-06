package com.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "department_staff")
public class DepartmentStaff extends Staff {
  

    public DepartmentStaff() {
        super();
   
    }
    public DepartmentStaff(String name, String email, String userName, String password, String departmentCode, String title) {
        super(name, email, userName, password, departmentCode, title );
        super.setRole(Roles.DEPARTMENT_STAFF);
      
    }

}