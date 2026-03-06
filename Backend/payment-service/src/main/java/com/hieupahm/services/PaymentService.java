package com.hieupahm.services;

import com.hieupahm.domain.PaymentMethod;
import com.hieupahm.error.PaymentException;
import com.hieupahm.error.UserException;
import com.hieupahm.model.PaymentOrder;
import com.hieupahm.payload.dto.BookingDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.payload.req.PaymentRequest;
import com.hieupahm.payload.res.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
        PaymentResponse init(PaymentRequest request);

        void createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod)
                        throws PaymentException, UserException;

        PaymentOrder getPaymentOrderById(Long id) throws Exception;

        PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

}
