package com.example.demo.controller;

import com.example.demo.model.dto.*;
import com.example.demo.service.CategoryService;
import com.example.demo.util.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(categoryService.findAll(TenantContext.getTenantId()));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<CategoryResponse>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(categoryService.findByType(TenantContext.getTenantId(), type));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(request, TenantContext.getTenantId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request, TenantContext.getTenantId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id, TenantContext.getTenantId());
        return ResponseEntity.ok().build();
    }
}
