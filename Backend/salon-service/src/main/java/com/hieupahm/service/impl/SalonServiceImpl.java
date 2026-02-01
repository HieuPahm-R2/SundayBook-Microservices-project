package com.hieupahm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hieupahm.modal.Salon;
import com.hieupahm.payload.dto.SalonDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.service.SalonService;

@Service
public class SalonServiceImpl implements SalonService {

    @Override
    public Salon create(SalonDTO dto, UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Salon updateSalon(Long salonId, Salon salon) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSalon'");
    }

    @Override
    public List<Salon> getAllSalons() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSalons'");
    }

    @Override
    public Salon getSalonById(Long salonId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalonById'");
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
