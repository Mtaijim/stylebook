package com.example.booking_service.repository;

import com.example.booking_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingServiceRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByCustomerId(UUID Id);
    List<Booking> findBySalonId(UUID Id);


}
