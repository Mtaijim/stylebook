package com.example.service_offering.controller;

import com.example.service_offering.model.ServiceOffering;
import com.example.service_offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServiceBySalonId(
            @PathVariable UUID salonId,
            @RequestParam(required = false) UUID categoryId
            ){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId,categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }
    @GetMapping("/{Id}")
    public ResponseEntity<ServiceOffering> getServiceById(
            @PathVariable UUID Id
    ){
        return ResponseEntity.ok().body(serviceOfferingService.getServiceById(Id));
    }
    @GetMapping("/list/{Ids}")
    public ResponseEntity<Set<ServiceOffering>> getServiceByIds(
            @PathVariable Set<UUID> Ids
    ){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServicesByIds(Ids);
        return ResponseEntity.ok(serviceOfferings);
    }
}
