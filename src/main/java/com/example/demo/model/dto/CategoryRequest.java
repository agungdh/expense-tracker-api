package com.example.demo.model.dto;

import com.example.demo.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CategoryRequest(
    @NotBlank String name,
    @NotNull Category.CategoryType type
) {}
