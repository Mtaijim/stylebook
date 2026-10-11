package com.example.category_service.repository;

import com.example.category_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Set;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category,UUID> {

    Set<Category> findBySalonId(UUID SalonId);
}
