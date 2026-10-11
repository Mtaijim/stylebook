package com.example.Payment.service.Payload.dto;

import com.example.booking_service.domain.BookingStatus;
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
