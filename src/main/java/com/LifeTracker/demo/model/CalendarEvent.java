package com.LifeTracker.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;



@Entity
public class CalendarEvent {
    @Id @GeneratedValue
    private Long id;
    private String type;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String username; // para filtrar por usuario

    @ManyToOne
    @JoinColumn(name = "user_id") // el nombre puede ser distinto si quieres
    private AppUser appUser;

    // getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
