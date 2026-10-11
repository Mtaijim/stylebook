package com.example.booking_service.dto;

import com.example.booking_service.domain.BookingStatus;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class BookingDto {
    private UUID Id;
    private UUID salonId;
    private UUID customerId;
    private LocalDateTime startTime;
    private  LocalDateTime endTime;
    private Set<UUID> serviceIds;
    private BookingStatus status = BookingStatus.PENDING;
    private int totalPrice;
}
