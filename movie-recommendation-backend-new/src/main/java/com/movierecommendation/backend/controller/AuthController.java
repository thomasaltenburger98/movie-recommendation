package com.movierecommendation.backend.controller;


import com.movierecommendation.backend.model.Token;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/hello")
    public ResponseEntity<?> returnHelloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        User newUser = authService.register(username, password);
        if (newUser != null) {
            return ResponseEntity.ok(newUser.getUsername());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
    }

    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        User user = authService.authenticate(username, password);
        if (user != null) {
            Token token = authService.generateToken(user);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }*/

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, Long> request) {
        Long userId = request.get("user_id");
        boolean success = authService.logout(userId);
        if (success) {
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");

    }

    @GetMapping("/checkToken")
    public ResponseEntity<?> checkToken(@RequestParam String token) {
        boolean isValid = authService.verifyToken(token);
        return ResponseEntity.ok(isValid);
    }
}

