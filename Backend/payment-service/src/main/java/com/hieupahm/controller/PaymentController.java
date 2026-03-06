package com.hieupahm.controller;

import java.util.Map;

import com.hieupahm.domain.PaymentMethod;
import com.hieupahm.error.PaymentException;
import com.hieupahm.error.UserException;
import com.hieupahm.model.PaymentOrder;
import com.hieupahm.payload.dto.BookingDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.payload.req.PaymentRequest;
import com.hieupahm.payload.res.PaymentResponse;
import com.hieupahm.services.PaymentService;
import com.hieupahm.services.client.UserFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hieupahm.payload.res.IpnResponse;
import com.hieupahm.services.IpnHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final IpnHandler ipnHandler;
    private final PaymentService paymentService;
    private final UserFeignClient userService;

    @PostMapping("/save")
    public ResponseEntity<Void> savePayment(
            @RequestHeader("Authorization") String jwt, @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod) throws PaymentException, UserException {

        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        paymentService.createOrder(user, booking, paymentMethod);

        return ResponseEntity.ok(null);
    }
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> initPayment(@RequestBody PaymentRequest req) throws PaymentException {

        return ResponseEntity.ok(this.paymentService.init(req));
    }


    @GetMapping("/vnpay_ipn")
    IpnResponse processIpn(@RequestParam Map<String, String> params, @RequestBody PaymentOrder paymentOrder) {
        log.info("[VNPay Ipn] Params: {}", params);
        return ipnHandler.process(paymentOrder, params);
    }
}
