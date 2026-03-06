package com.hieupahm.services.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import com.hieupahm.error.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hieupahm.constant.Currency;
import com.hieupahm.constant.Locale;
import com.hieupahm.constant.Symbol;
import com.hieupahm.domain.PaymentMethod;
import com.hieupahm.domain.PaymentOrderStatus;
import com.hieupahm.domain.VNpayParams;
import com.hieupahm.model.PaymentOrder;
import com.hieupahm.payload.dto.BookingDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.payload.req.PaymentRequest;
import com.hieupahm.payload.res.PaymentResponse;
import com.hieupahm.repository.PaymentOrderRepository;
import com.hieupahm.services.CryptoService;
import com.hieupahm.services.PaymentService;
import com.hieupahm.util.DateUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    public static final String VERSION = "2.1.0";
    public static final String COMMAND = "pay";
    public static final String ORDER_TYPE = "190000";
    public static final long DEFAULT_MULTIPLIER = 100L;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${payment.vnpay.tmn-code}")
    private String tmnCode;

    @Value("${payment.vnpay.init-payment-url}")
    private String initPaymentPrefixUrl;

    @Value("${payment.vnpay.return-url}")
    private String returnUrlFormat;

    @Value("${payment.vnpay.timeout}")
    private Integer paymentTimeout;

    private final CryptoService cryptoService;

    private final PaymentOrderRepository paymentOrderRepository;


    @Override
    public PaymentResponse init(PaymentRequest request) {
        var amount = request.getAmount() * DEFAULT_MULTIPLIER; // 1. amount * 100
        var txnRef = request.getTxnRef(); // 2. bookingId
        var returnUrl = buildReturnUrl(txnRef); // 3. FE redirect by returnUrl
        var vnCalendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        var createdDate = DateUtil.formatVnTime(vnCalendar);
        vnCalendar.add(Calendar.MINUTE, paymentTimeout);
        var expiredDate = DateUtil.formatVnTime(vnCalendar); // 4. expiredDate for secure

        var ipAddress = request.getIpAddress();
        var orderInfo = buildPaymentDetail(request);
        var requestId = request.getRequestId();

        Map<String, String> params = new HashMap<>();

        params.put(VNpayParams.VERSION, VERSION);
        params.put(VNpayParams.COMMAND, COMMAND);

        params.put(VNpayParams.TMN_CODE, tmnCode);
        params.put(VNpayParams.AMOUNT, String.valueOf(amount));
        params.put(VNpayParams.CURRENCY, Currency.VND.getValue());

        params.put(VNpayParams.TXN_REF, txnRef);
        params.put(VNpayParams.RETURN_URL, returnUrl);

        params.put(VNpayParams.CREATED_DATE, createdDate);
        params.put(VNpayParams.EXPIRE_DATE, expiredDate);

        params.put(VNpayParams.IP_ADDRESS, ipAddress);
        params.put(VNpayParams.LOCALE, Locale.VIETNAM.getCode());

        params.put(VNpayParams.ORDER_INFO, orderInfo);
        params.put(VNpayParams.ORDER_TYPE, ORDER_TYPE);

        var initPaymentUrl = buildInitPaymentUrl(params);
        log.debug("[request_id={}] Init payment url: {}", requestId, initPaymentUrl);
        return PaymentResponse.builder()
                .payment_url(initPaymentUrl)
                .build();
    }



    private String buildPaymentDetail(PaymentRequest request) {
        return String.format("Thanh toan don dat phong %s", request.getTxnRef());
    }

    private String buildReturnUrl(String txnRef) {
        return String.format(returnUrlFormat, txnRef);
    }

    public boolean verifyIpn(Map<String, String> params) {
        var reqSecureHash = params.get(VNpayParams.SECURE_HASH);
        params.remove(VNpayParams.SECURE_HASH);
        params.remove(VNpayParams.SECURE_HASH_TYPE);
        var hashPayload = new StringBuilder();
        var fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        var itr = fieldNames.iterator();
        while (itr.hasNext()) {
            var fieldName = itr.next();
            var fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                // Build hash data
                hashPayload.append(fieldName);
                hashPayload.append(Symbol.EQUAL);
                hashPayload.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    hashPayload.append(Symbol.AND);
                }
            }
        }

        var secureHash = cryptoService.sign(hashPayload.toString());
        return secureHash.equals(reqSecureHash);
    }
    @SneakyThrows
    private String buildInitPaymentUrl(Map<String, String> params) {
        var hashPayload = new StringBuilder();
        var query = new StringBuilder();
        var fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames); // 1. Sort field names

        var itr = fieldNames.iterator();
        while (itr.hasNext()) {
            var fieldName = itr.next();
            var fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                // 2.1. Build hash data
                hashPayload.append(fieldName);
                hashPayload.append(Symbol.EQUAL);
                hashPayload.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                // 2.2. Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append(Symbol.EQUAL);
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    query.append(Symbol.AND);
                    hashPayload.append(Symbol.AND);
                }
            }
        }

        // 3. Build secureHash
        var secureHash = cryptoService.sign(hashPayload.toString());

        // 4. Finalize query
        query.append("&vnp_SecureHash=");
        query.append(secureHash);

        return initPaymentPrefixUrl + "?" + query;
    }

    @Override
    public void createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws PaymentException {

        long amount = booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setUserId(user.getId());
        order.setAmount(amount);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        order.setPaymentMethod(paymentMethod);

        paymentOrderRepository.save(order);

    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        Optional<PaymentOrder> optionalPaymentOrder = paymentOrderRepository.findById(id);
        if (optionalPaymentOrder.isEmpty()) {
            throw new Exception("payment order not found with id " + id);
        }
        return optionalPaymentOrder.get();
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentLinkId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentLinkId);
        if (paymentOrder == null) {
            throw new Exception("payment order not found with id " + paymentLinkId);
        }
        return paymentOrder;
    }


}
