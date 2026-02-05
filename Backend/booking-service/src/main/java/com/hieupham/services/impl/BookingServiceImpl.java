package com.hieupham.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hieupham.domain.BookingStatus;
import com.hieupham.model.Booking;
import com.hieupham.model.PaymentOrder;
import com.hieupham.model.SalonReport;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.ServiceOfferDTO;
import com.hieupham.payload.dto.UserDTO;
import com.hieupham.payload.req.BookingRequest;
import com.hieupham.services.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon,
            Set<ServiceOfferDTO> serviceOfferingSet) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBooking'");
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsByCustomer'");
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsBySalon'");
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingById'");
    }

    @Override
    public Booking bookingSucess(PaymentOrder order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bookingSucess'");
    }

    @Override
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBookingStatus'");
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalonReport'");
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsByDate'");
    }

}
