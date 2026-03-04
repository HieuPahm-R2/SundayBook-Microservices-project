package com.hieupham.services.client;

import com.hieupham.domain.PaymentMethod;
import com.hieupham.errors.PaymentException;
import com.hieupham.errors.UserException;

import com.hieupham.payload.dto.BookingDTO;
import com.hieupham.payload.req.PaymentRequest;
import com.hieupham.payload.res.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment")
public interface PaymentFeignClient {
    @PostMapping("/api/payments/save")
    public ResponseEntity<PaymentResponse> savePayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod) throws UserException, PaymentException;

    @PostMapping("/api/payments/create")
    public ResponseEntity<PaymentResponse> initPayment(@RequestBody PaymentRequest req) throws PaymentException;
}
