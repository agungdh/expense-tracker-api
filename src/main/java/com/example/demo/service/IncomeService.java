package com.example.demo.service;

import com.example.demo.model.dto.*;
import java.util.List;
import java.util.UUID;

public interface IncomeService {
    List<IncomeResponse> findAll(UUID tenantId);
    List<IncomeResponse> findByDateRange(UUID tenantId, String startDate, String endDate);
    IncomeResponse create(IncomeRequest request, UUID tenantId);
    IncomeResponse update(UUID id, IncomeRequest request, UUID tenantId);
    void delete(UUID id, UUID tenantId);
}
