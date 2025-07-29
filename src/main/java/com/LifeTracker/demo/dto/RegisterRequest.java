package com.LifeTracker.demo.dto;

public class RegisterRequest {
    private String email;
    private String password;
    private String name; // si pides nombre en el registro
    private String role;

    // Constructor vacío necesario para deserialización
    public RegisterRequest() {}

    // Getters y setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}