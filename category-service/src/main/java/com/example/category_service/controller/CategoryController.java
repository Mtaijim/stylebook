package com.example.category_service.controller;

import com.example.category_service.dto.CategoryDto;
import com.example.category_service.mapper.CategoryMapper;
import com.example.category_service.model.Category;
import com.example.category_service.service.categoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final categoryService categoryService;

    @GetMapping("/salon/{id}")
    public ResponseEntity<Set<CategoryDto>> getCategoriesBySalonId(
            @PathVariable UUID id
            ){
        Set<Category> categories = categoryService.getAllCategoriesBySalonId(id);
        Set<CategoryDto> categoryDtos= categories.stream()
                .map(CategoryMapper::mapToDto)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(categoryDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoriesById(
            @PathVariable UUID id
    ) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(CategoryMapper.mapToDto(category));
    }
}
