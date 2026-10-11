package com.example.service_offering.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class ServiceDto {


    private UUID id;

    private String name;

    private String description;

    private int price;


    private int duration;

    private UUID salonId;

    private UUID categoryId;

    private String image;
}
