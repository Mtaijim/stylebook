package com.example.booking_service.model;

import com.example.booking_service.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    private UUID salonId;

    private UUID customerId;

    private LocalDateTime startTime;

    private  LocalDateTime endTime;

    @ElementCollection
    private Set<UUID> serviceIds;

    private BookingStatus status = BookingStatus.PENDING;


    private int totalPrice;
}
