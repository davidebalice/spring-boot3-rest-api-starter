package com.restapistarter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.restapistarter.model.Category;
import com.restapistarter.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category getCategoryById(int categoryId) {
        return repository.findById(categoryId)
                .orElse(null);
    }

    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category updateCategory) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }

            Category existingCategory = repository.findById(id).get();

            if (updateCategory.getName() != null) {
                existingCategory.setName(updateCategory.getName());
            }
         
            repository.save(existingCategory);

            return new ResponseEntity<>("Client update successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String deleteCategory(@PathVariable("id") Integer idCategory) {
        if (idCategory != null) {
            Optional<Category> pOptional = repository.findById(idCategory);
            if (pOptional.isPresent()) {
                Category c = pOptional.get();
                repository.delete(c);
            } else {

            }
        }
        return "redirect:/categories";
    }

    public List<Category> searchCategories(String keyword) {
        return repository.searchCategories(keyword);
    }

}
