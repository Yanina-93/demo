package com.LifeTracker.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the income", example = "1")
    private Long id;
    
    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 2, max = 100)
    @Schema(description = "Description of the income", example = "Salary")
    private String description;

    @PositiveOrZero(message = "The amount must be zero or positive")
    @DecimalMin(value = "0.0", inclusive = false)
    @Schema(description = "Amount of the income", example = "3000.00")
    private Double amount;
    
    @Schema(description = "Date of the income", example = "2023-03-15")
    private java.sql.Date date;
    private String category;


    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

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
        return appUser;
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
        this.appUser = appUser;
    }


}