package com.hieupahm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieupahm.payload.response.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/users/home")
    public ResponseEntity<ApiResponse> HomeControllerHandler() {
        return ResponseEntity.status(
                HttpStatus.OK)
                .body(new ApiResponse(
                        "welcome to sundayBook property booking system, with User API - Made with HieuPahm-R2"));
    }
}