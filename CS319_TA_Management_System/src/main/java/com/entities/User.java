package com.entities;

import javax.management.relation.Role;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "role"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Admin.class, name = "ADMIN"),
  @JsonSubTypes.Type(value = TA.class, name = "TA"),
  @JsonSubTypes.Type(value = Instructor.class, name = "INSTRUCTOR"),
  @JsonSubTypes.Type(value = DepartmentStaff.class, name = "DEPARTMENT_STAFF"),
  @JsonSubTypes.Type(value = DepartmentChair.class, name = "DEPARTMENT_CHAIR"),
@JsonSubTypes.Type(value = Dean.class, name = "DEAN"),
  @JsonSubTypes.Type(value = User.class, name = "UNKNOWN")
})

@Entity
@Table(name = "users")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String name;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;
    
    public User( String name, String email, String userName, String password, Roles role) {
        this.email = email;
        this.username = userName;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public User() {
        name = null;
        email = null;
        username = null;
        password = null;
        role = Roles.UNKNOWN;
    }

    public Integer getId() {
        return id;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
