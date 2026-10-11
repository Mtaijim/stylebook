package com.example.booking_service.Service;

import com.example.booking_service.domain.BookingStatus;
import com.example.booking_service.dto.BookingRequest;
import com.example.booking_service.dto.SalonDto;
import com.example.booking_service.dto.ServiceDto;
import com.example.booking_service.dto.UserDto;
import com.example.booking_service.model.Booking;
import com.example.booking_service.model.SalonReport;
import com.example.booking_service.repository.BookingServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingServiceRepository bookingServiceRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDto userDto, SalonDto salonDto, Set<ServiceDto> serviceDtoSet) throws Exception {

       int totalDuration = serviceDtoSet.stream().mapToInt(ServiceDto::getDuration).sum();

        LocalDateTime bookingStartTime= booking.getStartTime();
        LocalDateTime bookingEndTime= bookingStartTime.plusMinutes(totalDuration);

 Boolean isTimeSlotAvailable = isTimeSlotAvailable(salonDto,bookingStartTime,bookingEndTime);
 int totalPrice = serviceDtoSet.stream().mapToInt(ServiceDto::getPrice).sum();
 Set<UUID> idList = serviceDtoSet.stream().map(ServiceDto::getId).collect(Collectors.toSet());
 Booking newBooking =new Booking();
newBooking.setCustomerId(userDto.getId());
newBooking.setSalonId(salonDto.getId());
newBooking.setStatus(BookingStatus.PENDING);
newBooking.setServiceIds(idList);
newBooking.setStartTime(bookingStartTime);
newBooking.setEndTime(bookingEndTime);
newBooking.setTotalPrice(totalPrice);

        return bookingServiceRepository.save(newBooking) ;
    }

    public Boolean isTimeSlotAvailable(SalonDto salonDto,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = getBookingsBySalon(salonDto.getId());
        LocalDateTime salonOpenTime = salonDto.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonEndTime = salonDto.getCloseTime().atDate(bookingEndTime.toLocalDate());

        if(bookingStartTime.isBefore(salonOpenTime)
        || bookingEndTime.isAfter(salonEndTime)){
            throw new Exception("Booking time must be within working hours ");
        }

        for (Booking existingBooking : existingBookings ){
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();
            if (bookingStartTime.isBefore(existingBookingStartTime)&&bookingEndTime.isAfter(existingBookingEndTime)){
                throw new Exception(" slot is not Available ");
            }

            if(bookingStartTime.isEqual(existingBookingStartTime)|| bookingEndTime.isEqual(existingBookingEndTime)){
                throw new Exception(" slot is not Available ");
            }

        }
        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(UUID customerId) {
        return bookingServiceRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(UUID salonId) {
        return bookingServiceRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(UUID id) {
        return bookingServiceRepository.
                findById(id).orElseThrow(()-> new RuntimeException("Id is invalid "));
    }

    @Override
    public Booking updateBooking(UUID bookingId, BookingStatus status) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingServiceRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, UUID salonId) {
     List<Booking> allBookings = getBookingsBySalon(salonId);
     if(date==null){
         return allBookings;
     }
    return allBookings.stream().filter(booking -> isSameDate(booking.getStartTime(),date)||
             isSameDate(booking.getEndTime(),date)).collect(Collectors.toList());

    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }



    @Override
    public SalonReport getSalonReport(UUID salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);
        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrice).sum();
        Integer totalBookings = bookings.size();
        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .collect(Collectors.toList());

        double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice).sum();

        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalRefund(totalRefund);
        report.setTotalEarning(totalEarnings);
        report.setTotalBookings(totalBookings);
        return report;
    }
}
