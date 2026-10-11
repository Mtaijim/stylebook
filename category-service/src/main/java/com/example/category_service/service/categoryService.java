package com.example.category_service.service;

import com.example.category_service.dto.CategoryDto;
import com.example.category_service.dto.salonDto;
import com.example.category_service.model.Category;

import java.util.Set;
import java.util.UUID;

public interface categoryService {
    Category saveCategory(CategoryDto categoryDto,  UUID ownerSalonId);
    Set<Category> getAllCategoriesBySalonId(UUID salonId);
    Category getCategoryById(UUID id);
    void deleteCategoryById(UUID id ,UUID ownerSalonId);

}
