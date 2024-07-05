package com.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.dto.SubcategoryDto;
import com.restapi.model.Subcategory;
import com.restapi.repository.SubcategoryRepository;
import com.restapi.service.SubcategoryService;
import com.restapi.utility.FormatResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs for Subcategory Resource", description = "SUBCATEGORIES CRUD REST APIs - Create Subcategory, Update Subcategory, Get Subcategory, Get All Subcategories, Delete Subcategory")
@RestController
@RequestMapping("/api/v1/subcategories/")
public class SubcategoryController {

    private final SubcategoryRepository repository;
    private final SubcategoryService service;

    public SubcategoryController(SubcategoryRepository repository, SubcategoryService service) {
        this.repository = repository;
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Get all subcategories of one category", description = "Retrieve a list of all subcategories of one category")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/idcat/{idcat}")
    public ResponseEntity<List<SubcategoryDto>> list(@PathVariable("idcat") int categoryId) {
        List<Subcategory> subcategories = repository.findByCategoryId(categoryId);
        List<SubcategoryDto> subcategoriesDto = subcategories.stream()
                .map(subcategory -> modelMapper.map(subcategory, SubcategoryDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(subcategoriesDto);
    }

    // Get single Subcategory Rest Api (get id by url)
    // http://localhost:8081/api/v1/subcategories/1
    @Operation(summary = "Get Subcategory By ID REST API", description = "Get Subcategory By ID REST API is used to get a single Subcategory from the database, get id by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}")
    public SubcategoryDto getById(@PathVariable Integer id) {
        return service.getSubcategoryById(id);
    }
    //

    // Add new Subcategory Rest Api
    // http://localhost:8081/api/v1/subcategories/add
    @Operation(summary = "Crate new Subcategory REST API", description = "Save new Subcategory on database")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping("/add")
    public ResponseEntity<FormatResponse> add(@Valid @RequestBody Subcategory p) {
        repository.save(p);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Subcategory created successfully!"),
                HttpStatus.CREATED);
    }
    //

    // Update Subcategory Rest Api
    // http://localhost:8081/api/v1/subcategories/1
    @Operation(summary = "Update Subcategory REST API", description = "Update Subcategory on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<FormatResponse> update(@PathVariable Integer id,
            @RequestBody SubcategoryDto updatedSubcategory) {
        return service.updateSubcategory(id, updatedSubcategory);
    }
    //

    // Delete Subcategory Rest Api
    // http://localhost:8081/api/v1/subcategories/1
    @Operation(summary = "Delete Subcategory REST API", description = "Delete Subcategory on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/{id}")
    public ResponseEntity<FormatResponse> delete(@PathVariable Integer id) {
        service.deleteSubcategory(id);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Subcategory deleted successfully!"),
                HttpStatus.OK);

    }
    //

    // Search Subcategory Rest Api
    // http://localhost:8081/api/v1/subcategories/search
    @Operation(summary = "Search Subcategory REST API", description = "Search Subcategory on database by filter")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/search")
    public List<Subcategory> searchCategories(@RequestParam("keyword") String keyword) {
        List<Subcategory> subcategories = service.searchSubcategories(keyword);
        return subcategories;
    }
    //

}
