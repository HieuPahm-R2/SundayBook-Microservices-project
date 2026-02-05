package com.hieupham.mapper;

import java.util.Set;

import com.hieupham.model.Booking;
import com.hieupham.payload.dto.BookingDTO;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.ServiceOfferDTO;
import com.hieupham.payload.dto.UserDTO;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking, Set<ServiceOfferDTO> serviceOfferingDTOS, SalonDTO salonDTO,
            UserDTO userDTO) {
        if (booking == null) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setServicesIds(booking.getServiceIds());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setTotalPrice(booking.getTotalPrice());

        // If services mapping is needed (convert from serviceIds to
        // ServiceOfferingDTOs)
        bookingDTO.setServices(serviceOfferingDTOS);
        bookingDTO.setCustomer(userDTO);
        bookingDTO.setSalon(salonDTO);

        return bookingDTO;
    }

    // Convert BookingDTO to Booking entity
    public static Booking toEntity(BookingDTO bookingDTO) {
        if (bookingDTO == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setSalonId(bookingDTO.getSalonId());
        booking.setCustomerId(bookingDTO.getCustomerId());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setServiceIds(bookingDTO.getServicesIds());
        booking.setStatus(bookingDTO.getStatus());
        booking.setTotalPrice(bookingDTO.getTotalPrice());

        // Additional logic for mapping services can be added if needed.

        return booking;
    }
}
