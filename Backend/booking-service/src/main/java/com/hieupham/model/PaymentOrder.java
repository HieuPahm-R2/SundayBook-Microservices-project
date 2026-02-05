package com.hieupham.model;

import com.hieupham.domain.PaymentMethod;
import com.hieupham.domain.PaymentOrderStatus;

import lombok.Data;

@Data
public class PaymentOrder {
    private Long id;

    private Long amount;

    private PaymentOrderStatus status;

    private PaymentMethod paymentMethod;

    private String paymentLinkId;

    private Long userId;

    private Long bookingId;
}
