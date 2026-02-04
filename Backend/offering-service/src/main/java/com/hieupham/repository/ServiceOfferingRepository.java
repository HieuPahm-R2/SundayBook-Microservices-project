package com.hieupham.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hieupham.modal.ServiceOffering;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    Set<ServiceOffering> findBySalonId(Long id);
}
