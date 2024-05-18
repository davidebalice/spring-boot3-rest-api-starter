package com.restapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.restapi.model.Category;
@Service
public interface CategoryService {
    Category getCategoryById(int categoryId);
    ResponseEntity<String> updateCategory(int id, Category updateCategory);
    String deleteCategory(Integer idCategory);
    List<Category> searchCategories(String keyword);
}
