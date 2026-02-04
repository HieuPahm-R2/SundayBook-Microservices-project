package com.hieupham.service;

import java.util.Set;

import com.hieupham.modal.ServiceOffering;
import com.hieupham.payload.dto.CategoryDTO;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.ServiceDTO;

public interface ServiceOfferingService {
    ServiceOffering createService(ServiceDTO service, SalonDTO salon, CategoryDTO category);

    ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId);

    ServiceOffering getServiceById(Long serviceId);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);
}
