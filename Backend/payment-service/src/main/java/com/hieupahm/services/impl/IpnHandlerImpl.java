package com.hieupahm.services.impl;

import com.hieupahm.constant.Symbol;
import com.hieupahm.domain.VNpayIpnResConst;
import com.hieupahm.domain.VNpayParams;
import com.hieupahm.error.BusinessException;
import com.hieupahm.payload.res.IpnResponse;
import com.hieupahm.services.IpnHandler;
import com.hieupahm.services.PaymentService;
import com.hieupahm.services.client.BookingFeignClient;
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
    @Override
    public IpnResponse process(Map<String, String> params) {
        if (!vnPayService.verifyIpn(params)) {
            return VNpayIpnResConst.SIGNATURE_FAILED;
        }

        IpnResponse response;
        var txnRef = params.get(VNpayParams.TXN_REF);
        try {
            var bookingId = Long.parseLong(txnRef);
            bookingService.markBooked(bookingId);
            response = VNpayIpnResConst.SUCCESS;
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


}
