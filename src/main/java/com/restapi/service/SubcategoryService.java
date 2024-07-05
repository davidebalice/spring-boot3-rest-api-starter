package com.restapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.dto.SubcategoryDto;
import com.restapi.model.Subcategory;
import com.restapi.utility.FormatResponse;
@Service
public interface SubcategoryService {
    SubcategoryDto getSubcategoryById(int subcategoryId);
    ResponseEntity<FormatResponse> updateSubcategory(int id, SubcategoryDto updateSubcategory);
    ResponseEntity<FormatResponse> deleteSubcategory(Integer idSubcategory);
    List<Subcategory> searchSubcategories(String keyword);
}
