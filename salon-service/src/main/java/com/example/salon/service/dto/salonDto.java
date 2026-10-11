package com.example.salon.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class salonDto {
    private UUID id;

    @NotBlank(message = "name is required")
    private String name;

    private List<String> images;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "phone number is required")
    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(message = "city is required")
    private String city;

    @NotNull(message = "ownerId is required")
    private UUID ownerId;

    @NotNull(message = "openTime is required")
    private LocalTime openTime;

    @NotNull(message = "closeTime is required")
    private LocalTime closeTime;


}
