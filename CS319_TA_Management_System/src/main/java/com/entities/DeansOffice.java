package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "deansoffice")
public class DeansOffice extends Staff{
    public DeansOffice() {
        super();
        super.setRole(Roles.DEANS_OFFICE);
    }

    public DeansOffice(String name, String email, String username, String password, String departmentCode) {
        super(name, email, username, password, departmentCode, Roles.DEANS_OFFICE);
    }
}
