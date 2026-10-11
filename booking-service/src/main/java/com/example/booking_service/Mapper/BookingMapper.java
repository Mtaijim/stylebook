package com.example.booking_service.Mapper;

import com.example.booking_service.dto.BookingDto;
import com.example.booking_service.dto.BookingRequest;
import com.example.booking_service.model.Booking;

public class BookingMapper {

    public static BookingDto toDto(Booking booking){
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setCustomerId(booking.getCustomerId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setEndTime(booking.getEndTime());
        bookingDto.setStartTime(booking.getStartTime());
        bookingDto.setTotalPrice(booking.getTotalPrice());
        bookingDto.setSalonId(booking.getSalonId());
        bookingDto.setServiceIds(booking.getServiceIds());

        return bookingDto;
    }
}
