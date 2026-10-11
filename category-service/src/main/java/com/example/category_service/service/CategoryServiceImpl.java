package com.example.category_service.service;

import com.example.category_service.dto.CategoryDto;
import com.example.category_service.dto.salonDto;
import com.example.category_service.exception.CategoryNotFoundException;
import com.example.category_service.exception.UnauthorizedActionException;
import com.example.category_service.model.Category;
import com.example.category_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements categoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(CategoryDto categoryDto,  UUID ownerSalonId) {
        Category newCategory = new Category();
        newCategory.setName(categoryDto.getName());
        newCategory.setImage(categoryDto.getImage());
//        newCategory.setSalonId(categoryDto.getSalonId());
        newCategory.setSalonId(ownerSalonId);
        return categoryRepository.save(newCategory);
    }



    @Override
    public Set<Category> getAllCategoriesBySalonId(UUID salonId) {
        return categoryRepository.findBySalonId(salonId);
    }
    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public void deleteCategoryById(UUID id, UUID ownerSalonId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (!category.getSalonId().equals(ownerSalonId)) {
            throw new UnauthorizedActionException("You do not have permission to delete this category");
        }

        categoryRepository.delete(category);
    }
}
