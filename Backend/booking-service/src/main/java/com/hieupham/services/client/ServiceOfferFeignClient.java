package com.hieupham.services.client;

import com.hieupham.payload.dto.ServiceOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient("service-offering")
public interface ServiceOfferFeignClient {
    @GetMapping("/api/service-offering/list/{ids}")
    public ResponseEntity<Set<ServiceOfferDTO>> getServicesByIds(
            @PathVariable Set<Long> ids);
}
