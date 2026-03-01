package com.hieupahm.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.hieupahm.payload.dto.SalonDTO;

@FeignClient("SALON")
public interface SalonFeignClient {
    @GetMapping("/api/salons/owner")
    public ResponseEntity<SalonDTO> getSalonByOwner(@RequestHeader("Authorization") String jwt) throws Exception;

    @GetMapping("/api/salons/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception;
}
