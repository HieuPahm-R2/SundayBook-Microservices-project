package com.hieupham.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hieupham.domain.BookingStatus;
import com.hieupham.model.Booking;
import com.hieupham.model.PaymentOrder;
import com.hieupham.model.SalonReport;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.ServiceOfferDTO;
import com.hieupham.payload.dto.UserDTO;
import com.hieupham.payload.req.BookingRequest;
import com.hieupham.repository.BookingRepository;
import com.hieupham.services.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public boolean isTimeSlotAvailable(SalonDTO salon, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime)
            throws Exception {
        List<Booking> existBookings = getBookingsBySalon(salon.getId());
        // lấy ngày mở/đóng của ngày mà khách hàng đặt
        LocalDateTime salonOpenTime = salon.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salon.getCloseTime().atDate(bookingStartTime.toLocalDate());
        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking time must be within salon's open hours.");
        }
        for (Booking exist : existBookings) {
            LocalDateTime existingStartTime = exist.getStartTime();
            LocalDateTime existingEndTime = exist.getEndTime();
            if ((bookingStartTime.isBefore(existingEndTime) && bookingEndTime.isAfter(existingStartTime))
                    || (bookingStartTime.isEqual(salonOpenTime)) || (bookingEndTime.isEqual(salonCloseTime))) {
                throw new Exception("This is not available, please choose another time.");
            }
        }
        return true;
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon,
            Set<ServiceOfferDTO> serviceOfferingSet) throws Exception {
        int totalDur = serviceOfferingSet.stream().mapToInt(ServiceOfferDTO::getDuration).sum();
        LocalDateTime timeStartBooking = booking.getStartTime();
        LocalDateTime timeEndBooking = timeStartBooking.plusMinutes(totalDur);
        // check availability

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, timeStartBooking, timeEndBooking);

        if (!isSlotAvailable) {
            throw new Exception("Slot is not available");
        }

        int totalPrice = serviceOfferingSet.stream()
                .mapToInt(ServiceOfferDTO::getPrice)
                .sum();

        Set<Long> idList = serviceOfferingSet.stream()
                .map(ServiceOfferDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setStartTime(timeStartBooking);
        newBooking.setEndTime(timeEndBooking);
        newBooking.setServiceIds(idList);
        newBooking.setTotalPrice(totalPrice);
        newBooking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(newBooking);
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return booking.orElse(null);
    }

    @Override
    public Booking bookingSucess(PaymentOrder order) {
        Booking existingBooking = getBookingById(order.getBookingId());
        existingBooking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(existingBooking);
    }

    @Override
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception {
        Booking existingBooking = getBookingById(bookingId);
        if (existingBooking == null) {
            throw new Exception("booking not found");
        }

        existingBooking.setStatus(status);
        return bookingRepository.save(existingBooking);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bks = getBookingsBySalon(salonId);
        // init object
        SalonReport slr = new SalonReport();
        // Total Earns: sum of total price for all bks
        Double totalEarns = bks.stream().mapToDouble(Booking::getTotalPrice).sum();
        // mount of bookings
        Integer totalBks = bks.size();
        // canceled bks: filter bks with status canceled
        List<Booking> cancelBks = bks.stream().filter(
                bk -> bk.getStatus().toString().equalsIgnoreCase("CANCELLED")).collect(Collectors.toList());
        // Refunds: Calculate based on cancelled bookings (same totalPrice as refunded)
        Double totalRefund = cancelBks.stream().mapToDouble(Booking::getTotalPrice).sum();

        slr.setTotalEarnings(totalEarns);
        slr.setTotalBookings(totalBks);
        slr.setCancelledBookings(cancelBks.size());
        slr.setTotalRefund(totalRefund);

        return slr;
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = bookingRepository.findBySalonId(salonId);

        if (date == null) {
            return allBookings;
        }

        return allBookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(), date) ||
                        isSameDate(booking.getEndTime(), date))
                .collect(Collectors.toList());
    }

}
