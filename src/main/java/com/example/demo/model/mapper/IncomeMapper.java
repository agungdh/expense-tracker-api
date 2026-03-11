package com.example.demo.model.mapper;

import com.example.demo.model.dto.*;
import com.example.demo.model.entity.*;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IncomeMapper {

    @Mapping(target = "category", ignore = true)
    Income toEntity(IncomeRequest request, UUID tenantId);

    @Mapping(target = "categoryName", expression = "java(entity.getCategory() != null ? entity.getCategory().getName() : null)")
    IncomeResponse toResponse(Income entity);
}
