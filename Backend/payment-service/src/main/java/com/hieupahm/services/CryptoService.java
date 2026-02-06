package com.hieupahm.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hieupahm.constant.DefaultValue;
import com.hieupahm.constant.ResponseCode;
import com.hieupahm.error.BusinessException;
import com.hieupahm.util.EncodingUtil;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CryptoService {
    private final Mac mac = Mac.getInstance(DefaultValue.HMAC_SHA512);

    @Value("${payment.vnpay.secret-key}")
    private String secretKey;

    public CryptoService() throws NoSuchAlgorithmException {
    }

    @PostConstruct
    void init() throws InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), DefaultValue.HMAC_SHA512);
        mac.init(secretKeySpec);
    }

    public String sign(String data) {
        try {
            return EncodingUtil.toHexString(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.VNPAY_SIGNING_FAILED);
        }
    }
}
