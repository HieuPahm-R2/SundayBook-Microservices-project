package com.hieupahm.service;

import java.util.List;
import java.util.Set;

import com.hieupahm.modal.Category;
import com.hieupahm.payload.dto.SalonDTO;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salon);

    // Get all Categories
    List<Category> getAllCategories();

    Set<Category> getAllCategoriesBySalon(Long id);

    Category getCategoryById(Long id) throws Exception;

    Category updateCategory(Long id, Category category) throws Exception;

    void deleteCategory(Long id) throws Exception;
}
