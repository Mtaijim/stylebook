package com.example.booking_service.model;

import lombok.Data;

import java.util.UUID;

@Data
public class SalonReport {
    private UUID salonId;
    private String salonName;
    private int totalEarning;
    private Integer totalBookings;
    private Integer cancelledBookings;
    private Double totalRefund;
}
