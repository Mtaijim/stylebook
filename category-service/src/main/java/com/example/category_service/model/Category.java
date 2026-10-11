package com.example.category_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id ;

    @Column(nullable = false)
    private String name;

    private String image;

    @Column(nullable = false)
    private UUID salonId;

}
