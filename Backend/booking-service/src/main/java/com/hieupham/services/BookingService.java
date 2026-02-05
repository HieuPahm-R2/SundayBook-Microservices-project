package com.hieupham.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.hieupham.domain.BookingStatus;
import com.hieupham.model.Booking;
import com.hieupham.model.PaymentOrder;
import com.hieupham.model.SalonReport;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.ServiceOfferDTO;
import com.hieupham.payload.dto.UserDTO;
import com.hieupham.payload.req.BookingRequest;

public interface BookingService {
    Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceOfferDTO> serviceOfferingSet)
            throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking> getBookingsBySalon(Long salonId);

    Booking getBookingById(Long bookingId);

    Booking bookingSucess(PaymentOrder order);

    Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception;

    SalonReport getSalonReport(Long salonId);

    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
}
