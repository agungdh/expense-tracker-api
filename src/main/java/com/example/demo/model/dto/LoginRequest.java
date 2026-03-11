package com.example.demo.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String subdomain,
    @NotBlank String password
) {}
