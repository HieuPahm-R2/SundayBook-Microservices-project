package com.hieupham.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hieupham.modal.ServiceOffering;
import com.hieupham.payload.dto.CategoryDTO;
import com.hieupham.payload.dto.SalonDTO;
import com.hieupham.payload.dto.ServiceDTO;
import com.hieupham.service.ServiceOfferingService;
import com.hieupham.service.client.CategoryFeignClient;
import com.hieupham.service.client.SalonFeignClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offering")
public class ServiceOfferController {
    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonService;
    private final CategoryFeignClient categoryService;

    @PostMapping("/salon-owner")
    public ResponseEntity<ServiceOffering> createService(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ServiceDTO service) throws Exception {

        SalonDTO salon = salonService.getSalonByOwner(jwt).getBody();

        CategoryDTO category = categoryService.getCategoryById(service.getCategory()).getBody();

        ServiceOffering createdService = serviceOfferingService.createService(service, salon, category);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    @PatchMapping("/{serviceId}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long serviceId,
            @RequestBody ServiceOffering service) throws Exception {
        ServiceOffering updatedService = serviceOfferingService.updateService(serviceId, service);
        if (updatedService != null) {
            return new ResponseEntity<>(updatedService, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId) {

        Set<ServiceOffering> services = serviceOfferingService.getAllServicesBySalonId(salonId, categoryId);

        return ResponseEntity.ok(services);

    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceOffering> getServiceById(@PathVariable Long serviceId) throws Exception {
        ServiceOffering service = serviceOfferingService.getServiceById(serviceId);
        if (service == null) {
            throw new Exception("service not found with id " + serviceId);
        }
        return ResponseEntity.ok(service);

    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(
            @PathVariable Set<Long> ids) {
        Set<ServiceOffering> services = serviceOfferingService.getServicesByIds(ids);

        return ResponseEntity.ok(services);

    }
}
