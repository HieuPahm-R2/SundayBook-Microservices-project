package com.hieupham.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hieupham.modal.ServiceOffering;
import com.hieupham.payload.dto.CategoryDTO;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.ServiceDTO;
import com.hieupham.repository.ServiceOfferingRepository;
import com.hieupham.service.ServiceOfferingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceOfferingImpl implements ServiceOfferingService {
    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public com.hieupham.modal.ServiceOffering createService(ServiceDTO service, SalonDTO salon, CategoryDTO category) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setName(service.getName());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());
        serviceOffering.setImage(service.getImage());
        serviceOffering.setSalonId(salon.getId());
        serviceOffering.setCategoryId(category.getId());
        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public com.hieupham.modal.ServiceOffering updateService(Long serviceId, com.hieupham.modal.ServiceOffering service)
            throws Exception {
        Optional<ServiceOffering> existingService = serviceOfferingRepository.findById(serviceId);
        if (existingService.isPresent()) {
            ServiceOffering updatedService = existingService.get();
            updatedService.setName(service.getName());
            updatedService.setDescription(service.getDescription());
            updatedService.setPrice(service.getPrice());
            updatedService.setDuration(service.getDuration());
            if (service.getImage() != null) {
                updatedService.setImage(service.getImage());
            }

            return serviceOfferingRepository.save(updatedService);
        } else {
            throw new Exception("Service not found");
        }
    }

    @Override
    public Set<com.hieupham.modal.ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId) {
        Set<ServiceOffering> sers = this.serviceOfferingRepository.findBySalonId(salonId);
        if (categoryId != null) {
            sers = sers.stream().filter(s -> s.getCategoryId() != null && s.getCategoryId().equals(categoryId))
                    .collect(Collectors.toSet());
        }
        return sers;
    }

    @Override
    public com.hieupham.modal.ServiceOffering getServiceById(Long serviceId) {
        Optional<ServiceOffering> service = serviceOfferingRepository.findById(serviceId);
        return service.orElse(null);
    }

    @Override
    public Set<com.hieupham.modal.ServiceOffering> getServicesByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);
    }

}
