package com.movierecommendation.backend.controller;


import com.movierecommendation.backend.model.Token;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.response.ApiResponse;
import com.movierecommendation.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

        // check if user already exists
        if (authService.userExists(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("User already exists", 409));
        }

        User newUser = authService.register(username, password);
        if (newUser != null) {
            return ResponseEntity.ok(new ApiResponse("Registration successful", 200));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Registration failed", 500));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        User user = authService.authenticate(username, password);
        if (user != null) {
            Token token = authService.generateToken(user);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid credentials", 401));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        boolean success = authService.logoutWithToken(token);
        if (success) {
            return ResponseEntity.ok(new ApiResponse("Logged out successfully", 200));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Logout failed", 500));
    }

    @GetMapping("/checkToken")
    public ResponseEntity<?> checkToken(@RequestParam String token) {
        boolean isValid = authService.verifyToken(token);
        return ResponseEntity.ok(isValid);
    }
}

