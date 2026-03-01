package com.hieupahm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hieupahm.error.UserException;
import com.hieupahm.mapper.SalonMapper;
import com.hieupahm.modal.Salon;
import com.hieupahm.payload.dto.SalonDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.service.SalonService;
import com.hieupahm.service.client.UserFeignClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/salons")
public class SalonController {

    private final SalonService salonService;
    private final UserFeignClient userService;

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(
            @RequestHeader("Authorization") String jwt,
            @RequestBody SalonDTO salon) throws UserException {
        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();

        Salon createdSalon = salonService.create(salon, user);

        SalonDTO salonDTO = SalonMapper.mapToDTO(createdSalon, user);

        return new ResponseEntity<>(salonDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(
            @PathVariable Long salonId,
            @RequestBody Salon salon) throws Exception {
        Salon updatedSalon = salonService.updateSalon(salonId, salon);
        UserDTO user = userService.getUserById(updatedSalon.getOwnerId()).getBody();

        SalonDTO salonDTO = SalonMapper.mapToDTO(updatedSalon, user);

        return new ResponseEntity<>(salonDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getAllSalons() throws UserException {
        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOS = new ArrayList<>();
        for (Salon salon1 : salons) {
            UserDTO owner = userService.getUserById(salon1.getOwnerId()).getBody();
            SalonDTO apply = SalonMapper.mapToDTO(salon1, owner);
            salonDTOS.add(apply);
        }
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(@PathVariable Long salonId) throws Exception {
        Salon salon = salonService.getSalonById(salonId);
        if (salon == null) {
            throw new Exception("salon not exist with id " + salonId);
        }
        UserDTO user = userService.getUserById(salon.getOwnerId()).getBody();

        SalonDTO salonDTO = SalonMapper.mapToDTO(salon, user);

        return ResponseEntity.ok(salonDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalon(@RequestParam("city") String city) throws Exception {
        List<Salon> salons = salonService.searchSalonByCityName(city);
        List<SalonDTO> salonDTOS = new ArrayList<>();
        for (Salon salon1 : salons) {
            UserDTO owner = userService.getUserById(salon1.getOwnerId()).getBody();
            SalonDTO apply = SalonMapper.mapToDTO(salon1, owner);
            salonDTOS.add(apply);
        }
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/owner")
    public ResponseEntity<Salon> getSalonByOwner(@RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserFromJwtToken(jwt).getBody();
        System.out.println("salon " + user);
        Salon salon = salonService.getSalonByOwnerId(user.getId());

        return ResponseEntity.ok(salon);
    }
}
