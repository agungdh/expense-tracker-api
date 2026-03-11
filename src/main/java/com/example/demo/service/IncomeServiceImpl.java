package com.example.demo.service;

import com.example.demo.model.dto.*;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Income;
import com.example.demo.model.mapper.IncomeMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final IncomeMapper incomeMapper;

    @Override
    public List<IncomeResponse> findAll(UUID tenantId) {
        return incomeRepository.findByTenantId(tenantId).stream()
            .map(incomeMapper::toResponse)
            .toList();
    }

    @Override
    public List<IncomeResponse> findByDateRange(UUID tenantId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return incomeRepository.findByTenantIdAndTransactionDateBetween(tenantId, start, end).stream()
            .map(incomeMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public IncomeResponse create(IncomeRequest request, UUID tenantId) {
        Income entity = incomeMapper.toEntity(request, tenantId);
        
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                .filter(c -> c.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Category not found"));
            entity.setCategory(category);
        }
        
        return incomeMapper.toResponse(incomeRepository.save(entity));
    }

    @Override
    @Transactional
    public IncomeResponse update(UUID id, IncomeRequest request, UUID tenantId) {
        Income entity = incomeRepository.findById(id)
            .filter(i -> i.getTenantId().equals(tenantId))
            .orElseThrow(() -> new RuntimeException("Income not found"));

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

        return incomeMapper.toResponse(incomeRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID tenantId) {
        Income entity = incomeRepository.findById(id)
            .filter(i -> i.getTenantId().equals(tenantId))
            .orElseThrow(() -> new RuntimeException("Income not found"));
        
        incomeRepository.delete(entity);
    }
}
