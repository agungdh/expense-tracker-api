package com.example.demo.model.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IncomeRequest(
    @NotNull BigDecimal amount,
    String description,
    @NotNull LocalDate transactionDate,
    UUID categoryId
) {}
