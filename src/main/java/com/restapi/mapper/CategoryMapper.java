package com.restapi.mapper;

import com.restapi.dto.CategoryDto;
import com.restapi.model.Category;

public class CategoryMapper {

    // Convert Category JPA Entity into CategoryDto
    public static CategoryDto mapToCategoryDto(Category p) {
        CategoryDto CategoryDto = new CategoryDto(
                p.getId(),
                p.getName(),
                p.getDescription());
        return CategoryDto;
    }

    // Convert CategoryDto into Category JPA Entity
    public static Category mapToCategory(Category category) {
        Category p = new Category(
                category.getId(),
                category.getName(),
                category.getDescription(), 0, null, null);
        return p;
    }
}
