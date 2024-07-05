package com.restapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.restapi.dto.CategoryDto;
import com.restapi.dto.SubcategoryDto;
import com.restapi.model.Category;
import com.restapi.model.Subcategory;

public class CategoryMapper {

    // Convert Category JPA Entity into CategoryDto
    public static CategoryDto mapToCategoryDto(Category category) {
        List<SubcategoryDto> subcategoryDtos = category.getSubcategories()
                .stream()
                .map(CategoryMapper::mapToSubcategoryDto)
                .collect(Collectors.toList());

        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                subcategoryDtos);
    }

    // Convert Subcategory JPA Entity into SubcategoryDto
    public static SubcategoryDto mapToSubcategoryDto(Subcategory subcategory) {
        return new SubcategoryDto(
                subcategory.getId(),
                subcategory.getName(),
                subcategory.getDescription(),
                subcategory.getCategory().getId());
    }

    // Convert CategoryDto into Category JPA Entity
    public static Category mapToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        List<Subcategory> subcategories = categoryDto.getSubcategories()
                .stream()
                .map(CategoryMapper::mapToSubcategory)
                .collect(Collectors.toList());

        subcategories.forEach(category::addSubcategory);
        return category;
    }

    // Convert SubcategoryDto into Subcategory JPA Entity
    public static Subcategory mapToSubcategory(SubcategoryDto subcategoryDto) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(subcategoryDto.getId());
        subcategory.setName(subcategoryDto.getName());
        subcategory.setDescription(subcategoryDto.getDescription());

        return subcategory;
    }
}
