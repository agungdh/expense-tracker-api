package com.example.demo.service;

import com.example.demo.model.dto.*;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    List<ExpenseResponse> findAll(UUID tenantId);
    List<ExpenseResponse> findByDateRange(UUID tenantId, String startDate, String endDate);
    ExpenseResponse create(ExpenseRequest request, UUID tenantId);
    ExpenseResponse update(UUID id, ExpenseRequest request, UUID tenantId);
    void delete(UUID id, UUID tenantId);
}
