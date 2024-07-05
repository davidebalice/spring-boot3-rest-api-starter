package com.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.dto.SubcategoryDto;
import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.Category;
import com.restapi.model.Subcategory;
import com.restapi.repository.CategoryRepository;
import com.restapi.repository.SubcategoryRepository;
import com.restapi.service.SubcategoryService;
import com.restapi.utility.FormatResponse;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository repository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SubcategoryServiceImpl(SubcategoryRepository repository,CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SubcategoryDto getSubcategoryById(int subcategoryId) {
        Subcategory subcategory = repository.findById(subcategoryId).orElseThrow(
                () -> new ResourceNotFoundException("Subcategory", "id"));
        return modelMapper.map(subcategory, SubcategoryDto.class);
    }

    @Override
    public ResponseEntity<FormatResponse> updateSubcategory(int subcategoryId, SubcategoryDto updateSubcategory) {
        try {
            if (!repository.existsById(subcategoryId)) {
                throw new ResourceNotFoundException("Subcategory", "id");
            }

            Subcategory existingSubcategory = repository.findById(subcategoryId).orElse(null);

            if (updateSubcategory.getName() != null) {
                existingSubcategory.setName(updateSubcategory.getName());
            }
            if (updateSubcategory.getDescription() != null) {
                existingSubcategory.setDescription(updateSubcategory.getDescription());
            }
            if (updateSubcategory.getCategoryId() != 0) {
                Category category = categoryRepository.findById(updateSubcategory.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id"));
                existingSubcategory.setCategory(category);
            }

            repository.save(existingSubcategory);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Subcategory updated successfully!"),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating subcategory"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<FormatResponse> deleteSubcategory(Integer subcategoryId) {
        Optional<Subcategory> pOptional = repository.findById(subcategoryId);
        if (pOptional.isPresent()) {
            Subcategory c = pOptional.get();
            repository.delete(c);
            return new ResponseEntity<FormatResponse>(new FormatResponse("Subcategory deleted successfully"),
                    HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Subcategory", "id");
        }
    }

    @Override
    public List<Subcategory> searchSubcategories(String keyword) {
        return repository.searchSubcategories(keyword);
    }
}
