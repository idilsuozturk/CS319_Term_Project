package com.entities;

public class Dean extends Staff{
    
    public Dean() {
        super();
        super.setRole(Roles.DEAN);
    }

    public Dean(String email, String userName, String password, String departmentCode, String title) {
        super(email, userName, password, departmentCode, title);
        super.setRole(Roles.DEAN);
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