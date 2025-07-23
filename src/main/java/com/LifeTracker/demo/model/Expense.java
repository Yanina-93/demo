package com.LifeTracker.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 2, max = 100)
    private String description;

    @PositiveOrZero
    @DecimalMin(value = "0.0", inclusive = false)
    private Double amount;
    private java.sql.Date date;
    private String category;


    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser AppUser;

    // Getters y setters...
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public AppUser getAppUser() {
        return AppUser;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setDate(java.sql.Date date) {
        this.date = date;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setAppUser(AppUser appUser) {
        this.AppUser = appUser;
    }
    
}