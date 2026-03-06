package com.hieupahm.services.impl;

import com.hieupahm.domain.PaymentOrderStatus;
import com.hieupahm.domain.VNpayIpnResConst;
import com.hieupahm.domain.VNpayParams;
import com.hieupahm.error.BusinessException;
import com.hieupahm.messaging.BookingEventProducer;
import com.hieupahm.messaging.NotificationEventProducer;
import com.hieupahm.model.PaymentOrder;
import com.hieupahm.payload.dto.BookingDTO;
import com.hieupahm.payload.res.IpnResponse;
import com.hieupahm.repository.PaymentOrderRepository;
import com.hieupahm.services.IpnHandler;
import com.hieupahm.services.PaymentService;
import com.hieupahm.services.client.BookingFeignClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class IpnHandlerImpl implements IpnHandler {

    private final PaymentServiceImpl vnPayService;

    private final BookingFeignClient bookingService;
    private final PaymentOrderRepository paymentOrderRepository;
    private final NotificationEventProducer notificationEventProducer;
    private final BookingEventProducer bookingEventProducer;

    @Override
    public IpnResponse process(PaymentOrder paymentOrder, Map<String, String> params) {
        if (!vnPayService.verifyIpn(params)) {
            return VNpayIpnResConst.SIGNATURE_FAILED;
        }

        IpnResponse response;
        var txnRef = params.get(VNpayParams.TXN_REF);
        try {
            var bookingId = Long.parseLong(txnRef);
            bookingService.markBooked(bookingId);
            response = VNpayIpnResConst.SUCCESS;
            // sent message queue
            notificationEventProducer.sentNotificationEvent(
                    paymentOrder.getBookingId(),
                    paymentOrder.getUserId(),
                    paymentOrder.getSalonId());

            bookingEventProducer.sentBookingUpdateEvent(paymentOrder);

            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepository.save(paymentOrder);
        }
        catch (BusinessException e) {
            switch (e.getResponseCode()) {
                case BOOKING_NOT_FOUND -> response = VNpayIpnResConst.ORDER_NOT_FOUND;
                default -> response = VNpayIpnResConst.UNKNOWN_ERROR;
            }
        }
        catch (Exception e) {
            response = VNpayIpnResConst.UNKNOWN_ERROR;
        }

        log.info("[VNPay Ipn] txnRef: {}, response: {}", txnRef, response);
        return response;

    }


}
