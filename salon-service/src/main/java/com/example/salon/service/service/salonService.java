package com.example.salon.service.service;

import com.example.salon.service.dto.salonDto;
import com.example.salon.service.dto.UserDto;
import com.example.salon.service.model.Salon;

import java.util.List;
import java.util.UUID;

public interface salonService {
    Salon createSalon(salonDto salon, UserDto user);
    Salon updateSalon(salonDto salon, UserDto user, UUID salonId) ;
    List<Salon> getAllSalons();
    Salon getSalonById(UUID salonId) ;
    Salon getSalonByOwnerId(UUID ownerId);
    List<Salon> searchSalonByCityName(String city);
}
