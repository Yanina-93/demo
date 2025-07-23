package com.LifeTracker.demo.repository;

import com.LifeTracker.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    // Methods
}
