package com.example.category_service.controller;

import com.example.category_service.dto.CategoryDto;
import com.example.category_service.dto.salonDto;
import com.example.category_service.mapper.CategoryMapper;
import com.example.category_service.model.Category;
import com.example.category_service.service.categoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor
public class SalonCategoryController {

    private final categoryService categoryService;
    private static final UUID HARDCODED_OWNER_SALON_ID =
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    private UUID currentOwnerSalonId() {
        return HARDCODED_OWNER_SALON_ID;
    }

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        Category savedCategory = categoryService.saveCategory(categoryDto, currentOwnerSalonId());
        return ResponseEntity.ok(CategoryMapper.mapToDto(savedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable UUID id
    ) {
        categoryService.deleteCategoryById(id, currentOwnerSalonId());
        return ResponseEntity.ok("Category deleted successfully");
    }
}

