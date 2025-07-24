package com.LifeTracker.demo.security;

import com.LifeTracker.demo.model.AppUser;
import com.LifeTracker.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Does not exist an user with the email: " + email));
        return User.builder()
            .username(user.getEmail())
            .password(user.getPasswordHash())
            .roles("USER") // roles can be fetched from user entity if needed
            .build();
    }
}

