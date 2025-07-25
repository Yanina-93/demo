package com.LifeTracker.demo.model;

import jakarta.persistence.*;
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
    @JoinColumn(name = "AppUser_id")
    private AppUser AppUser; // can be null if global

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
        return AppUser;
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
        this.AppUser = appUser;
    }

}