package com.example.category_service.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(UUID categoryId){
        super("Category not found with id: "+ categoryId);

    }
    private CategoryNotFoundException(String message){
        super(message);
    }
}
