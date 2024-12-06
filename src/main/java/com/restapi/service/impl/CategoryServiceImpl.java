package com.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.dto.CategoryDto;
import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.Category;
import com.restapi.repository.CategoryRepository;
import com.restapi.service.CategoryService;
import com.restapi.utility.FormatResponse;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAllCategoriesOrderedByNameAsc() {
        return repository.findAllByOrderByNameAsc();
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id"));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public ResponseEntity<FormatResponse> updateCategory(int categoryId, Category updateCategory) {
        try {
            if (!repository.existsById(categoryId)) {
                throw new ResourceNotFoundException("Category", "id");
            }

            Category existingCategory = repository.findById(categoryId).orElse(null);

            if (updateCategory.getName() != null) {
                existingCategory.setName(updateCategory.getName());
            }
            if (updateCategory.getDescription() != null) {
                existingCategory.setDescription(updateCategory.getDescription());
            }

            repository.save(existingCategory);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Category updated successfully!"),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating category"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<FormatResponse> deleteCategory(Integer categoryId) {
        Optional<Category> pOptional = repository.findById(categoryId);
        if (pOptional.isPresent()) {
            Category c = pOptional.get();
            repository.delete(c);
            return new ResponseEntity<FormatResponse>(new FormatResponse("Category deleted successfully"),
                    HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Category", "id");
        }
    }

    @Override
    public List<Category> searchCategories(String keyword) {
        return repository.searchCategories(keyword);
    }
}
