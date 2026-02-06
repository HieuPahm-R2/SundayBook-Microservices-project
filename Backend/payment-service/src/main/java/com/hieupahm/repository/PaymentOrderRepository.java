package com.hieupahm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hieupahm.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    PaymentOrder findByPaymentLinkId(String paymentId);
}