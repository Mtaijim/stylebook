package com.example.salon.service.mapper;

import com.example.salon.service.dto.salonDto;
import com.example.salon.service.model.Salon;

public class SalonMapper {
    private SalonMapper() {

    }
    public static salonDto  mapToDto(Salon salon){
        salonDto salonDto = new salonDto();
        salonDto.setId(salon.getId());
        salonDto.setName(salon.getName());
        salonDto.setAddress(salon.getAddress());
        salonDto.setEmail(salon.getEmail());
        salonDto.setImages(salon.getImages());
        salonDto.setPhoneNumber(salon.getPhoneNumber());
        salonDto.setCity(salon.getCity());
        salonDto.setOwnerId(salon.getOwnerId());
        salonDto.setOpenTime(salon.getOpenTime());
        salonDto.setCloseTime(salon.getCloseTime());
return salonDto;
    }
}
