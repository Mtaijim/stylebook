package com.example.service_offering.repository;

import com.example.service_offering.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ServiceOfferingServiceRepository extends JpaRepository<ServiceOffering, UUID> {
    Set<ServiceOffering> findBySalonId(UUID salonId);

    List<ServiceOffering> findAllById(Set<Long> ids);

}
