package com.example.demo.model.mapper;

import com.example.demo.model.dto.*;
import com.example.demo.model.entity.*;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {

    @Mapping(target = "category", ignore = true)
    Expense toEntity(ExpenseRequest request, UUID tenantId);

    @Mapping(target = "categoryName", expression = "java(entity.getCategory() != null ? entity.getCategory().getName() : null)")
    ExpenseResponse toResponse(Expense entity);
}
