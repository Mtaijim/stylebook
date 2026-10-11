package com.example.booking_service.Service;

import com.example.booking_service.domain.BookingStatus;
import com.example.booking_service.dto.BookingRequest;
import com.example.booking_service.dto.ServiceDto;
import com.example.booking_service.dto.UserDto;
import com.example.booking_service.dto.SalonDto;
import com.example.booking_service.model.Booking;
import com.example.booking_service.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BookingService {

    Booking createBooking(BookingRequest booking ,
                          UserDto userDto,
                           SalonDto salonDto,
                          Set<ServiceDto> serviceDtoSet
                          ) throws Exception;

    List<Booking> getBookingsByCustomer(UUID customerId);
    List<Booking> getBookingsBySalon(UUID salonId);
    Booking getBookingById(UUID id);
    Booking updateBooking(UUID bookingId, BookingStatus status);
    List<Booking>  getBookingsByDate(LocalDate date,UUID salonId);
    SalonReport getSalonReport(UUID salonId);
}
