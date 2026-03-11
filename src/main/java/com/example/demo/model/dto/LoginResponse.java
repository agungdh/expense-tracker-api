package com.example.demo.model.dto;

public record LoginResponse(
    String token,
    String type
) {}
