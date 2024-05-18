package com.restapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.dto.CategoryDto;
import com.restapi.mapper.CategoryMapper;
import com.restapi.model.Category;
import com.restapi.repository.CategoryRepository;
import com.restapi.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories/")
public class CategoryController {

    private final CategoryRepository repository;
    private final CategoryService service;

    public CategoryController(CategoryRepository repository, CategoryService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Category's Rest Api", description = "This API extracts all categories")
    public ResponseEntity<List<CategoryDto>> list() {
        List<Category> categories = (List<Category>) repository.findAll();
        List<CategoryDto> categoriesDto = categories.stream()
                .map(CategoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriesDto);
    }

   @GetMapping("/{id}")
    @Operation(summary = "Category's Rest Api", description = "This API extracts one Category")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer id) {
        Optional<Category> category = repository.findById(id);
        if (category.isPresent()) {
            CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(category.get());
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public String add(@Valid @RequestBody Category p) {
        repository.save(p);
        return "redirect:/api/categories/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("categoryData") Category c) {
        repository.save(c);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Category updatedCategory) {
        return service.updateCategory(id, updatedCategory);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        return service.deleteCategory(id);
    }

    @GetMapping("/search")
    public List<Category> searchCategories(@RequestParam("keyword") String keyword) {
        List<Category> categories = service.searchCategories(keyword);
        return categories;
    }

}
