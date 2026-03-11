package com.example.demo.model.dto;

import com.example.demo.model.entity.Category;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(
    UUID id,
    String name,
    Category.CategoryType type,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
