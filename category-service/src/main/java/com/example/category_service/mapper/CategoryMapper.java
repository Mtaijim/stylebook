package com.example.category_service.mapper;

import com.example.category_service.dto.CategoryDto;
import com.example.category_service.model.Category;

public class CategoryMapper {
    private CategoryMapper() {
    }
    public static CategoryDto mapToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImage(category.getImage());
        dto.setSalonId(category.getSalonId());
        return dto;
    }
}
