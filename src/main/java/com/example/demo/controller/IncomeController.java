package com.example.demo.controller;

import com.example.demo.model.dto.*;
import com.example.demo.service.IncomeService;
import com.example.demo.util.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<IncomeResponse>> findAll() {
        return ResponseEntity.ok(incomeService.findAll(TenantContext.getTenantId()));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<IncomeResponse>> findByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(incomeService.findByDateRange(TenantContext.getTenantId(), startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<IncomeResponse> create(@Valid @RequestBody IncomeRequest request) {
        return ResponseEntity.ok(incomeService.create(request, TenantContext.getTenantId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody IncomeRequest request) {
        return ResponseEntity.ok(incomeService.update(id, request, TenantContext.getTenantId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        incomeService.delete(id, TenantContext.getTenantId());
        return ResponseEntity.ok().build();
    }
}
