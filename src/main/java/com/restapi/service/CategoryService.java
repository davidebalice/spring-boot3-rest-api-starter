package com.restapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.dto.CategoryDto;
import com.restapi.model.Category;
import com.restapi.utility.FormatResponse;

@Service
public interface CategoryService {
    CategoryDto getCategoryById(int categoryId);

    public List<Category> getAllCategoriesOrderedByNameAsc();

    ResponseEntity<FormatResponse> updateCategory(int id, Category updateCategory);

    ResponseEntity<FormatResponse> deleteCategory(Integer idCategory);

    List<Category> searchCategories(String keyword);
}
