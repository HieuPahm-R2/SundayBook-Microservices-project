package com.hieupham.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hieupham.payload.dto.CategoryDTO;

@FeignClient("CATEGORY")
public interface CategoryFeignClient {
    @GetMapping("/api/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id);
}
