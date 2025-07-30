package com.LifeTracker.demo.service;
import com.LifeTracker.demo.dto.RegisterRequest;
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

    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    // Devuelve Optional.empty() si el registro fue exitoso, o el Optional con el usuario si ya existe
    public Optional<AppUser> register(String email, String password, String role, String name) {
        Optional<AppUser> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            return existing;
        }
        AppUser user = new AppUser();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return Optional.empty();
    }
}
