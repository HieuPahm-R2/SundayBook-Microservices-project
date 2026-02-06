package com.hieupahm.payload.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.hieupahm.domain.BookingStatus;

import lombok.Data;

@Data
public class BookingDTO {
    private Long id;

    private Long salonId;

    private Long customerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Set<Long> servicesIds;

    private BookingStatus status;

    private int totalPrice;
}
