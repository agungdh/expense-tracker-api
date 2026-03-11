package com.example.demo.repository;

import com.example.demo.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByTenantId(UUID tenantId);
    List<Expense> findByTenantIdAndTransactionDateBetween(UUID tenantId, LocalDate start, LocalDate end);
    List<Expense> findByTenantIdAndCategoryId(UUID tenantId, UUID categoryId);
}
