package com.hieupahm.services;

import com.hieupahm.domain.PaymentMethod;
import com.hieupahm.error.UserException;
import com.hieupahm.model.PaymentOrder;
import com.hieupahm.payload.dto.BookingDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.payload.res.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentLinkResponse createOrder(UserDTO user,
            BookingDTO booking, PaymentMethod paymentMethod) throws UserException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

    Boolean ProceedPaymentOrder(PaymentOrder paymentOrder,
            String paymentId, String paymentLinkId) throws StripeException;

    String createStripePaymentLink(UserDTO user, Long Amount,
            Long orderId) throws StripeException;
}
