package com.dto;

public class UserDTO {
    private String username;
    private String name;
    private String email;
    private String role;

    public UserDTO(String username, String name, String email, String role) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
    }

}