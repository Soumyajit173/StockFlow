package com.guvi.projects.StockFlow.controller;

import com.guvi.projects.StockFlow.dto.AuthRequest;
import com.guvi.projects.StockFlow.dto.AuthResponse;
import com.guvi.projects.StockFlow.entity.User;
import com.guvi.projects.StockFlow.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}