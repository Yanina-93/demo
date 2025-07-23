package com.LifeTracker.demo.model;

import jakarta.persistence.*;

@Entity
public class MotivationalQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    private java.sql.Date date;

    @ManyToOne
    @JoinColumn(name = "AppUser_id")
    private AppUser AppUser; // puede ser null si es global

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