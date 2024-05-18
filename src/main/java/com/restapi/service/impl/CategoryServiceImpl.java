package com.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.model.Category;
import com.restapi.repository.CategoryRepository;
import com.restapi.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public ResponseEntity<String> updateCategory(int id, Category updateCategory) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }

            Category existingCategory = repository.findById(id).orElse(null);

            if (updateCategory.getName() != null) {
                existingCategory.setName(updateCategory.getName());
            }

            repository.save(existingCategory);

            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String deleteCategory(Integer idCategory) {
        if (idCategory != null) {
            Optional<Category> pOptional = repository.findById(idCategory);
            if (pOptional.isPresent()) {
                Category c = pOptional.get();
                repository.delete(c);
            }
        }
        return "redirect:/categories";
    }

    @Override
    public List<Category> searchCategories(String keyword) {
        return repository.searchCategories(keyword);
    }
}
