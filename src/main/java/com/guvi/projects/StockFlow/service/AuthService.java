package com.guvi.projects.StockFlow.service;

import com.guvi.projects.StockFlow.dto.AuthRequest;
import com.guvi.projects.StockFlow.dto.AuthResponse;
import com.guvi.projects.StockFlow.entity.User;

public interface AuthService {

    User register(User user);

    AuthResponse login(AuthRequest request);
}