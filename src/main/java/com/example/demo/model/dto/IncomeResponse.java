package com.example.demo.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record IncomeResponse(
    UUID id,
    BigDecimal amount,
    String description,
    LocalDate transactionDate,
    UUID categoryId,
    String categoryName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
