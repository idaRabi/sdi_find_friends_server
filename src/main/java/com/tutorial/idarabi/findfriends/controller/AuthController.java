package com.tutorial.idarabi.findfriends.controller;

import com.tutorial.idarabi.findfriends.dto.LoginDto;
import com.tutorial.idarabi.findfriends.dto.UserRegistrationDto;
import com.tutorial.idarabi.findfriends.model.User;
import com.tutorial.idarabi.findfriends.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            User user = userService.createUser(registrationDto);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", user.getId());
            response.put("username", user.getUsername());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            User user = null;

            // Try to find user by username first, then by email
            Optional<User> userByUsername = userService.findByUsername(loginDto.getUsernameOrEmail());
            if (userByUsername.isPresent()) {
                user = userByUsername.get();
            } else {
                Optional<User> userByEmail = userService.findByEmail(loginDto.getUsernameOrEmail());
                if (userByEmail.isPresent()) {
                    user = userByEmail.get();
                }
            }

//            if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            if (user != null && loginDto.getPassword().equals(user.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("userId", user.getId());
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                response.put("fullName", user.getFullName());

                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}