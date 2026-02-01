package com.hieupahm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hieupahm.modal.Salon;

public interface SalonRepository extends JpaRepository<Salon, Long> {
    Salon findByOwnerId(Long ownerId);

    @Query("SELECT s FROM Salon s WHERE" + "(LOWER(s.city) LIKE (LOWER(CONCAT('%',:keyword,'%')))) OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "s.active = true")
    List<Salon> searchSalons(@Param("keyword") String keyword);
    // tìm không phân biệt hoa – thường
}
