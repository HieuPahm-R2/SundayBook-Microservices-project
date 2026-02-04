package com.hieupahm.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hieupahm.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findBySalonId(Long id);
}
