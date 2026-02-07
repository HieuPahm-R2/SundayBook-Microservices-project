package com.hieupahm.services;

import java.util.Map;

import com.hieupahm.payload.res.IpnResponse;

public interface IpnHandler {
    IpnResponse process(Map<String, String> params);
}
