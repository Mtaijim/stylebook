package com.example.booking_service.controller;

import com.example.booking_service.Mapper.BookingMapper;
import com.example.booking_service.Service.BookingService;
import com.example.booking_service.domain.BookingStatus;
import com.example.booking_service.dto.*;
import com.example.booking_service.model.Booking;
import com.example.booking_service.model.SalonReport;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class BookingController {


    private final BookingService bookingService;

    private static final UUID HARDCODED_OWNER_SALON_ID =
            UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

    private static final UUID HARDCODED_OWNER_SERVICE_ID =
            UUID.fromString("550e8400-e29b-41d4-a716-446655440003");

    private static final UUID HARDCODED_OWNER_customer_ID =
            UUID.fromString("550e8400-e29b-41d4-a716-446655440005");

    @PostMapping()
    public ResponseEntity<Booking> createBooking(
            @RequestParam UUID salonId,
            @RequestBody BookingRequest bookingRequest
            ) throws Exception {


        UserDto user = new UserDto();
        user.setId(HARDCODED_OWNER_SALON_ID);

        SalonDto salon  = new SalonDto();
        salon.setId(salonId);
//        salon.setOpenTime( );

        Set<ServiceDto> serviceDtoSet = new HashSet<>();
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(HARDCODED_OWNER_SERVICE_ID);
        serviceDto.setPrice(399);
        serviceDto.setDuration(45);
        serviceDto.setName("Hair cut for men");


        serviceDtoSet.add(serviceDto);

        Booking booking = bookingService.createBooking(bookingRequest,
                user,salon,serviceDtoSet);
        return ResponseEntity.ok(booking) ;
    }


    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDto>> getBookingByCustomer(){
        List<Booking> bookings=bookingService.getBookingsByCustomer(HARDCODED_OWNER_customer_ID);
        return  ResponseEntity.ok(getBookingDtos(bookings));
        
    }
    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDto>> getBookingBySalon(){
        List<Booking> bookings=bookingService.getBookingsBySalon(HARDCODED_OWNER_SALON_ID);
        return  ResponseEntity.ok(getBookingDtos(bookings));

    }


    private @Nullable Set<BookingDto> getBookingDtos(List<Booking> bookings) {
        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDto(booking);
                }).collect(Collectors.toSet());
    }
    @GetMapping("/{BookingId}")
    public ResponseEntity<BookingDto> getBookingById(
            @PathVariable UUID BookingId
    ){
        Booking booking=bookingService.getBookingById(BookingId);
        return  ResponseEntity.ok(BookingMapper.toDto(booking));

    }

    @PutMapping ("/{BookingId}/status")
    public ResponseEntity<BookingDto> updateBooking(
            @PathVariable UUID BookingId,
            @RequestBody BookingStatus status
            ){
        Booking booking=bookingService.updateBooking(BookingId,status);
        return  ResponseEntity.ok(BookingMapper.toDto(booking));

    }
    @GetMapping("/slot/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDto>> getBookedSlot(
            @PathVariable UUID salonId,
            @RequestParam(required = false) LocalDate date
            ){

        List<Booking> bookings=bookingService.getBookingsByDate(date,salonId);

List<BookingSlotDto> slotDtos =bookings.stream()
        .map(booking -> {
            BookingSlotDto slotDto = new BookingSlotDto();
            slotDto.setStartTime(booking.getStartTime());
            slotDto.setEndTime(booking.getEndTime());

            return slotDto;

        }).collect(Collectors.toList());
        return  ResponseEntity.ok(slotDtos);

    }
    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(

    ){
    SalonReport report =bookingService.getSalonReport(HARDCODED_OWNER_SALON_ID);

        return  ResponseEntity.ok(report);

    }
}
