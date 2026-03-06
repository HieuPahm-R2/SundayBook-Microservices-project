package com.hieupham.controller;

import com.hieupham.domain.BookingStatus;
import com.hieupham.domain.PaymentMethod;
import com.hieupham.errors.UserException;
import com.hieupham.mapper.BookingMapper;
import com.hieupham.model.Booking;
import com.hieupham.model.SalonReport;
import com.hieupham.payload.dto.*;
import com.hieupham.payload.req.BookingRequest;
import com.hieupham.payload.req.PaymentRequest;
import com.hieupham.payload.res.BookingResponse;
import com.hieupham.services.BookingService;

import com.hieupham.services.client.PaymentFeignClient;
import com.hieupham.services.client.SalonFeignClient;
import com.hieupham.services.client.ServiceOfferFeignClient;
import com.hieupham.services.client.UserFeignClient;
import com.hieupham.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final UserFeignClient userService;
    private final SalonFeignClient salonService;
    private final ServiceOfferFeignClient serviceOfferingService;
    private final PaymentFeignClient paymentFeignClient;


    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestHeader("Authorization") String jwt, @RequestParam Long salonId, PaymentMethod paymentMethod,
            @RequestBody BookingRequest bookingRequest, HttpServletRequest httpServletRequest) throws Exception {
        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        bookingRequest.setIpAddress(ipAddress);
        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        SalonDTO salon = salonService.getSalonById(salonId).getBody();

        assert salon != null;
        if (salon.getId() == null) {
            throw new Exception("Salon not found");
        }

        Set<ServiceOfferDTO> services = serviceOfferingService.getServicesByIds(bookingRequest.getServiceIds()).getBody();

        BookingResponse res = bookingService.createBooking(bookingRequest, user, salon, services);

        paymentFeignClient.savePayment(jwt, res.getBooking(),paymentMethod);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    /**
     * Get all bookings for a customer
     */
    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(
            @RequestHeader("Authorization") String jwt)
            throws UserException {

        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        List<Booking> bookings = bookingService.getBookingsByCustomer(user.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));

    }

    /**
     * Get all bookings for a salon
     */
    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        SalonDTO salon = salonService.getSalonByOwner(jwt).getBody();

        SalonReport report = bookingService.getSalonReport(salon.getId());

        return ResponseEntity.ok(report);

    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(

            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        SalonDTO salon = salonService.getSalonByOwner(jwt).getBody();

        List<Booking> bookings = bookingService.getBookingsBySalon(salon.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));

    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {

        return bookings.stream()
                .map(booking -> {
                    UserDTO user;
                    Set<ServiceOfferDTO> offeringDTOS = serviceOfferingService
                            .getServicesByIds(booking.getServiceIds()).getBody();

                    SalonDTO salonDTO;
                    try {
                        salonDTO = salonService.getSalonById(
                                booking.getSalonId()).getBody();
                        user = userService.getUserById(booking.getCustomerId()).getBody();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return BookingMapper.toDTO(
                            booking,
                            offeringDTOS,
                            salonDTO, user);
                })
                .collect(Collectors.toSet());
    }

    /**
     * Get a booking by its ID
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        Set<ServiceOfferDTO> offeringDTOS = serviceOfferingService
                .getServicesByIds(booking.getServiceIds()).getBody();

        BookingDTO bookingDTO = BookingMapper.toDTO(booking,
                offeringDTOS, null, null);

        return ResponseEntity.ok(bookingDTO);

    }

    /**
     * Update the status of a booking
     */
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> markBooked(
            @PathVariable Long bookingId) throws Exception {

        Booking updatedBooking = bookingService.updateBookingStatus(bookingId);

        Set<ServiceOfferDTO> offeringDTOS = serviceOfferingService
                .getServicesByIds(updatedBooking.getServiceIds()).getBody();

        SalonDTO salonDTO;
        try {
            salonDTO = salonService.getSalonById(
                    updatedBooking.getSalonId()).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        BookingDTO bookingDTO = BookingMapper.toDTO(updatedBooking,
                offeringDTOS,
                salonDTO, null);

        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);

    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookedSlotDTO>> getBookedSlots(
            @PathVariable Long salonId,
            @PathVariable LocalDate date,
            @RequestHeader("Authorization") String jwt) throws Exception {

        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);

        List<BookedSlotDTO> slotsDTOS = bookings.stream()
                .map(booking -> {
                    BookedSlotDTO slotDto = new BookedSlotDTO();

                    slotDto.setStartTime(booking.getStartTime());
                    slotDto.setEndTime(booking.getEndTime());

                    return slotDto;
                })
                .toList();

        return ResponseEntity.ok(slotsDTOS);

    }
}
