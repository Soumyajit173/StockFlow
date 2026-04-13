package com.guvi.projects.StockFlow.dto;

public record AuthRequest(
        String email,
        String password
) {}