package com.example.demo.repository;

import com.example.demo.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    List<Income> findByTenantId(UUID tenantId);
    List<Income> findByTenantIdAndTransactionDateBetween(UUID tenantId, LocalDate start, LocalDate end);
    List<Income> findByTenantIdAndCategoryId(UUID tenantId, UUID categoryId);
}
