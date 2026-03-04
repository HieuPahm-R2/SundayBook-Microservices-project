package com.hieupahm.services.client;

import com.hieupahm.payload.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("booking")
public interface BookingFeignClient {
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> markBooked(@PathVariable Long bookingId) throws Exception;
}
