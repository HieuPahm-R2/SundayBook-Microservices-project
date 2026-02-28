package com.hieupahm.services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.hieupahm.error.UserException;
import com.hieupahm.payload.dto.UserDTO;

@FeignClient("USER")
public interface UserFeignClient {
    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDTO> getUserFromJwtToken(@RequestHeader("Authorization") String jwt) throws UserException;
}
