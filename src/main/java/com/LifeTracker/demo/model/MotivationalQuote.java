package com.LifeTracker.demo.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class MotivationalQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the quote", example = "1")
    private Long id;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Text of the quote", example = "Believe in yourself!")
    private String text;

    @Schema(description = "Date of the quote", example = "2023-03-15")
    private java.sql.Date date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "appUser_id")
    private AppUser appUser; // can be null if global

    // Getters y setters...
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }
    
    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

}