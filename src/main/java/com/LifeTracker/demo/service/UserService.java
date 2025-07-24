package com.LifeTracker.demo.service;
import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser register(String email, String password, String role) {
        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }
}
