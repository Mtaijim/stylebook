package com.example.category_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryDto {

    private UUID id;
    @NotBlank(message = "name is required")
    private String name;
    private String image;
    private UUID salonId;
}
