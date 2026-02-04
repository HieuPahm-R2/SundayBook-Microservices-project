package com.hieupahm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hieupahm.modal.Salon;
import com.hieupahm.payload.dto.SalonDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.repository.SalonRepository;
import com.hieupahm.service.SalonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepository;

    @Override
    public Salon create(SalonDTO dto, UserDTO user) {
        Salon salon = new Salon();
        salon.setName(dto.getName());
        salon.setImages(dto.getImages());
        salon.setCity(dto.getCity());
        salon.setAddress(dto.getAddress());
        salon.setEmail(dto.getEmail());
        salon.setPhoneNumber(dto.getPhoneNumber());
        salon.setOpenTime(dto.getOpenTime());
        salon.setCloseTime(dto.getCloseTime());
        salon.setHomeService(true);
        salon.setOpen(true);
        salon.setOwnerId(user.getId());
        salon.setActive(true);

        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(Long salonId, Salon salon) throws Exception {
        Salon existingSalon = getSalonById(salonId);
        if (existingSalon != null) {

            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setCity(salon.getCity());
            existingSalon.setOpen(salon.isOpen());
            existingSalon.setHomeService(salon.isHomeService());
            existingSalon.setActive(salon.isActive());
            existingSalon.setOpenTime(salon.getOpenTime());
            existingSalon.setCloseTime(salon.getCloseTime());

            return salonRepository.save(existingSalon);
        }
        throw new Exception("salon not exist");
    }

    @Override
    public List<Salon> getAllSalons() {
        // TODO Auto-generated method stub
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) {
        return salonRepository.findById(salonId).orElse(null);
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalonByOwnerId'");
    }

    @Override
    public List<Salon> searchSalonByCityName(String city) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchSalonByCityName'");
    }

}
