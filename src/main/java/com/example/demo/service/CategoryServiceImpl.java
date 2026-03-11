package com.example.demo.service;

import com.example.demo.model.dto.*;
import com.example.demo.model.entity.Category;
import com.example.demo.model.mapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll(UUID tenantId) {
        return categoryRepository.findByTenantId(tenantId).stream()
            .map(categoryMapper::toResponse)
            .toList();
    }

    @Override
    public List<CategoryResponse> findByType(UUID tenantId, String type) {
        Category.CategoryType categoryType = Category.CategoryType.valueOf(type.toUpperCase());
        return categoryRepository.findByTenantIdAndType(tenantId, categoryType).stream()
            .map(categoryMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request, UUID tenantId) {
        Category entity = categoryMapper.toEntity(request);
        entity.setTenantId(tenantId);
        return categoryMapper.toResponse(categoryRepository.save(entity));
    }

    @Override
    @Transactional
    public CategoryResponse update(UUID id, CategoryRequest request, UUID tenantId) {
        Category entity = categoryRepository.findById(id)
            .filter(c -> c.getTenantId().equals(tenantId))
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        entity.setName(request.name());
        entity.setType(request.type());
        
        return categoryMapper.toResponse(categoryRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID tenantId) {
        Category entity = categoryRepository.findById(id)
            .filter(c -> c.getTenantId().equals(tenantId))
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        categoryRepository.delete(entity);
    }
}
