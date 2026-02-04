package com.hieupahm.payload.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalonDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String city;
    private boolean isOpen;
    private boolean homeService;
    private boolean active;
    private Long ownerId;
    private LocalTime openTime;
    private LocalTime closeTime;
    private List<String> images;
}
