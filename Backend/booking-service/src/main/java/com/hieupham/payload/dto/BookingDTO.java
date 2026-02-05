package com.hieupham.payload.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.hieupham.domain.BookingStatus;

import lombok.Data;

@Data
public class BookingDTO {
    private Long id;

    private Long salonId;

    private SalonDTO salon;

    private Long customerId;

    private UserDTO customer;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Set<Long> servicesIds;

    private Set<ServiceOfferDTO> services;

    private BookingStatus status;

    private int totalPrice;
}
