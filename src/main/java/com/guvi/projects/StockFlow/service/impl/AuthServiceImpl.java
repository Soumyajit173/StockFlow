package com.guvi.projects.StockFlow.service.impl;

import com.guvi.projects.StockFlow.dto.AuthRequest;
import com.guvi.projects.StockFlow.dto.AuthResponse;
import com.guvi.projects.StockFlow.entity.User;
import com.guvi.projects.StockFlow.exception.BadRequestException;
import com.guvi.projects.StockFlow.exception.ResourceNotFoundException;
import com.guvi.projects.StockFlow.repository.UserRepository;
import com.guvi.projects.StockFlow.security.JwtUtil;
import com.guvi.projects.StockFlow.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}