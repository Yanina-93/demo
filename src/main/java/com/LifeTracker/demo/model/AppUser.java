package com.LifeTracker.demo.model;


import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

//import org.antlr.v4.runtime.misc.NotNull;
//import org.hibernate.annotations.NotFound;
import jakarta.validation.constraints.*;
//import io.micrometer.common.lang.NonNull;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;

    @NotEmpty(message = "This field cannot be empty")

    @Size(min = 2, max = 50)
    @Schema(description = "Name of the user", example = "John Doe")
    private String name;

    @NotEmpty(message = "This field cannot be empty")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "This field cannot be empty")
    @Size(min = 6, max = 100)
    @Schema(description = "Password hash of the user", example = "hashed_password")
    private String passwordHash;

    private Timestamp registrationDate = new Timestamp(System.currentTimeMillis());

     private String role; // "USER", "ADMIN", etc.

    //Relationships with other entities
    @OneToMany(mappedBy = "AppUser", cascade = CascadeType.ALL)
    private List<Event> events;
    @OneToMany(mappedBy = "AppUser", cascade = CascadeType.ALL)
    private List<Income> incomes;

    @OneToMany(mappedBy = "AppUser", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "AppUser", cascade = CascadeType.ALL)
    private List<MotivationalQuote> motivationalQuotes;

    // getters y setters

    public String getRole() {
        return role;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<MotivationalQuote> getMotivationalQuotes() {
        return motivationalQuotes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
}
