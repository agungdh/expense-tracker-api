package com.example.demo.service;

import com.example.demo.model.dto.*;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Expense;
import com.example.demo.model.mapper.ExpenseMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseResponse> findAll(UUID tenantId) {
        return expenseRepository.findByTenantId(tenantId).stream()
            .map(expenseMapper::toResponse)
            .toList();
    }

    @Override
    public List<ExpenseResponse> findByDateRange(UUID tenantId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return expenseRepository.findByTenantIdAndTransactionDateBetween(tenantId, start, end).stream()
            .map(expenseMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public ExpenseResponse create(ExpenseRequest request, UUID tenantId) {
        Expense entity = expenseMapper.toEntity(request, tenantId);
        
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                .filter(c -> c.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Category not found"));
            entity.setCategory(category);
        }
        
        return expenseMapper.toResponse(expenseRepository.save(entity));
    }

    @Override
    @Transactional
    public ExpenseResponse update(UUID id, ExpenseRequest request, UUID tenantId) {
        Expense entity = expenseRepository.findById(id)
            .filter(e -> e.getTenantId().equals(tenantId))
            .orElseThrow(() -> new RuntimeException("Expense not found"));

        entity.setAmount(request.amount());
        entity.setDescription(request.description());
        entity.setTransactionDate(request.transactionDate());

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                .filter(c -> c.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Category not found"));
            entity.setCategory(category);
        } else {
            entity.setCategory(null);
        }

        return expenseMapper.toResponse(expenseRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID tenantId) {
        Expense entity = expenseRepository.findById(id)
            .filter(e -> e.getTenantId().equals(tenantId))
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        
        expenseRepository.delete(entity);
    }
}
