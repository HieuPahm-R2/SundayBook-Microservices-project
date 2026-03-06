package com.hieupahm.services;

import java.util.Map;

import com.hieupahm.model.PaymentOrder;
import com.hieupahm.payload.res.IpnResponse;

public interface IpnHandler {
    IpnResponse process(PaymentOrder paymentOrder, Map<String, String> params);
}
