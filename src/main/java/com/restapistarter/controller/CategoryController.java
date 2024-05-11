package com.restapistarter.controller;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.restapistarter.model.Category;
import com.restapistarter.repository.CategoryRepository;
import com.restapistarter.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {

    @Autowired
    CategoryRepository repository;

    @Autowired
    CategoryService service;

    @GetMapping("/")
    @Operation(summary = "Category's Rest Api", description = "This API extracts all categories")
    public Iterable<Category> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @RouterOperation(operation = @io.swagger.v3.oas.annotations.Operation(summary = "Category's Rest Api", description = "This API extracts one Category"))
    public Category getById(@PathVariable Integer id) {
        return repository.findById(id).get();
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
