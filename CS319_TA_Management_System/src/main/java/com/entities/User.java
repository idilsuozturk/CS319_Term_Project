package com.entities;

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
  @JsonSubTypes.Type(value = DeansOffice.class, name = "DEANS_OFFICE"),
  @JsonSubTypes.Type(value = User.class, name = "UNKNOWN")
})

@Entity
@Table(name = "users")
@Inheritance(strategy = jakarta.persistence.InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;
    
    public User( String name, String email, String username, String password, Roles role) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
        this.name = null;
        this.email = null;
        this.username = null;
        this.password = null;
        this.role = Roles.UNKNOWN;
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
