package com.hieupahm.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieupahm.modal.Category;
import com.hieupahm.payload.dto.SalonDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.service.CategoryService;
import com.hieupahm.service.client.SalonFeignClient;
import com.hieupahm.service.client.UserFeignClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserFeignClient userService;
    private final SalonFeignClient salonService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
            @RequestHeader("Authorization") String jwt) throws Exception {

        SalonDTO salon = salonService.getSalonByOwner(jwt).getBody();

        Category savedCategory = categoryService.saveCategory(category, salon);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Get all Categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Get all Categories by Salon ID
    @GetMapping("/salon/{id}")
    public ResponseEntity<Set<Category>> getCategoriesBySalon(@PathVariable Long id) throws Exception {
        SalonDTO salon = salonService.getSalonById(id).getBody();

        Set<Category> categories = categoryService.getAllCategoriesBySalon(salon.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Get a Category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category) throws Exception {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // Delete a Category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
