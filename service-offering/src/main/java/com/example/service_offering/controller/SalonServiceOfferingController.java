package com.example.service_offering.controller;


import com.example.service_offering.dto.CategoryDto;
import com.example.service_offering.dto.ServiceDto;
import com.example.service_offering.dto.salonDto;
import com.example.service_offering.model.ServiceOffering;
import com.example.service_offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private static final UUID HARDCODED_SALON_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa7");
    private static final UUID HARDCODED_Category_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa8");


    @PostMapping()
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDto serviceDto){
        salonDto  salonDto  = new salonDto();
        salonDto.setId(HARDCODED_SALON_ID);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(serviceDto.getCategoryId());

        ServiceOffering serviceOfferings =
                serviceOfferingService.createService(salonDto,serviceDto,categoryDto);
        return ResponseEntity.ok(serviceOfferings);

    }


    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> UpdateService(
            @RequestBody UUID id ,
            @RequestBody ServiceOffering serviceOffering

    ){
        ServiceOffering serviceOfferings =
                serviceOfferingService.updateService(id,serviceOffering);
        return ResponseEntity.ok(serviceOfferings);

    }

}
