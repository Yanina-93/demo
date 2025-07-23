package com.LifeTracker.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 2, max = 100)
    private String title;

    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 2, max = 50)
    private String type; // clase, trabajo, estudio, descanso

    private java.sql.Date date;
    private java.sql.Time startTime;
    private Integer duration; // minutos

    @ManyToOne
    @JoinColumn(name = "AppUser_id")
    private AppUser AppUser;

    // Getters y setters...
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public java.sql.Time getStartTime() {
        return startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public AppUser getAppUser() {
        return  AppUser;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public void setStartTime(java.sql.Time startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setAppUser(AppUser AppUser) {
        this.AppUser = AppUser;
    }


}