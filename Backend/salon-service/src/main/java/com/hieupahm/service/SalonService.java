package com.hieupahm.service;

import java.util.List;

import com.hieupahm.modal.Salon;
import com.hieupahm.payload.dto.SalonDTO;
import com.hieupahm.payload.dto.UserDTO;

public interface SalonService {
    Salon create(SalonDTO dto, UserDTO user);

    Salon updateSalon(Long salonId, Salon salon) throws Exception;

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId);

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCityName(String city);
}
