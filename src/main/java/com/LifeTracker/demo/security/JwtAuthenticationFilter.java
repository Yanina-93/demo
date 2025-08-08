package com.LifeTracker.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if the request is for an API endpoint
        String path = request.getRequestURI();
        if (!path.startsWith("/api/")) {
            // For requests that are NOT /api/, do nothing special
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("Entering JWT filter for: " + path);
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            String username = null;
            try {
                username = jwtUtil.extractUsername(token);
                System.out.println("Token recibido: " + token);
                System.out.println("Username extracted: " + username);
            } catch (Exception e) {
                System.out.println("Error extracting username: " + e.getMessage());
            }

            if (username != null && jwtUtil.isTokenValid(token)) {
                System.out.println("Valid token, setting authentication for: " + username);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                System.out.println("Invalid token or null username");
            }
        } else {
            System.out.println("No Authorization header found or not Bearer");
        }

        filterChain.doFilter(request, response);
    }
}
