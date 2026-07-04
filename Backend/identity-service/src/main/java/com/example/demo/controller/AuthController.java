package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates user credentials and generates JWT token.
     *
     * @param request authentication request containing username and password
     * @return JWT token if authentication is successful
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.getUsername());

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    /**
     * Registers a new user.
     *
     * @param request registration request containing user details
     * @return success message or error response
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User request) {

        if (dao.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        dao.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
