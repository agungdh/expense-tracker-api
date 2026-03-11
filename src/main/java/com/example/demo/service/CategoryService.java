package com.example.demo.service;

import com.example.demo.model.dto.*;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> findAll(UUID tenantId);
    List<CategoryResponse> findByType(UUID tenantId, String type);
    CategoryResponse create(CategoryRequest request, UUID tenantId);
    CategoryResponse update(UUID id, CategoryRequest request, UUID tenantId);
    void delete(UUID id, UUID tenantId);
}
