package com.example.service_offering.service;

import com.example.service_offering.dto.CategoryDto;
import com.example.service_offering.dto.ServiceDto;
import com.example.service_offering.dto.salonDto;
import com.example.service_offering.model.ServiceOffering;


import java.util.Set;
import java.util.UUID;

public interface ServiceOfferingService {

    ServiceOffering createService(salonDto salonDto
            , ServiceDto serviceDto, CategoryDto categoryDto );

    ServiceOffering updateService(UUID serviceId, ServiceOffering service);
   Set<ServiceOffering> getAllServiceBySalonId(UUID salonId, UUID categoryId);
Set<ServiceOffering> getAllServicesByIds(Set<UUID> ids);

ServiceOffering getServiceById(UUID id);
}
