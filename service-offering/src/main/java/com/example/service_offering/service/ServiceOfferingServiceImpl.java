package com.example.service_offering.service;

import com.example.service_offering.dto.CategoryDto;
import com.example.service_offering.dto.ServiceDto;
import com.example.service_offering.dto.salonDto;
import com.example.service_offering.model.ServiceOffering;
import com.example.service_offering.repository.ServiceOfferingServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService{

    private final ServiceOfferingServiceRepository serviceOfferingServiceRepository;

    @Override
    public ServiceOffering createService(salonDto salonDto, ServiceDto serviceDto, CategoryDto categoryDto) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(String.valueOf(salonDto.getImages()));
        serviceOffering.setSalonId(salonDto.getId());
        serviceOffering.setName(serviceDto.getName());
        serviceOffering.setDescription(serviceDto.getDescription());
        serviceOffering.setCategoryId(categoryDto.getId());
        serviceOffering.setPrice(serviceDto.getPrice());
        serviceOffering.setDuration(serviceDto.getDuration());

        return  serviceOfferingServiceRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(UUID serviceId, ServiceOffering service) {

        ServiceOffering serviceOffering = serviceOfferingServiceRepository.findById(serviceId)
                .orElseThrow(()-> new RuntimeException(" service id not found"));

        serviceOffering.setName(service.getName());
        serviceOffering.setImage(service.getImage());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());
        return serviceOfferingServiceRepository.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(UUID salonId, UUID categoryId) {

        Set<ServiceOffering> services = serviceOfferingServiceRepository
                .findBySalonId(salonId);
        if(categoryId != null){
            services = services.stream().filter((service)->service.getCategoryId() !=null
            && service.getCategoryId() == categoryId).collect(Collectors.toSet());


        }
        return services;
    }

    @Override
    public Set<ServiceOffering> getAllServicesByIds(Set<UUID> ids) {
        List<ServiceOffering> services = serviceOfferingServiceRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(UUID id) {
        return serviceOfferingServiceRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Invalid Id :"+ id));
    }
}
