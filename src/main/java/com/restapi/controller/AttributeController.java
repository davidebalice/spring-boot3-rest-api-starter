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

import com.restapi.config.DemoMode;
import com.restapi.exception.DemoModeException;
import com.restapi.model.Attribute;
import com.restapi.repository.AttributeRepository;
import com.restapi.service.AttributeService;
import com.restapi.utility.FormatResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs for Attribute Resource", description = "ATTRIBUTES CRUD REST APIs - Create Attribute, Update Attribute, Get Attribute, Get All Attributes, Delete Attribute")
@RestController
@RequestMapping("/api/v1/attributes/")
public class AttributeController {

    private final AttributeRepository repository;
    private final AttributeService service;

    public AttributeController(AttributeRepository repository, AttributeService service) {
        this.repository = repository;
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DemoMode demoMode;

    // Get all Attributes Rest Api
    // http://localhost:8081/api/v1/attributes
    @Operation(summary = "Get all attributes", description = "Retrieve a list of all attributes")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/")
    public ResponseEntity<List<Attribute>> list() {
        List<Attribute> attributes = (List<Attribute>) repository.findAll();
        return ResponseEntity.ok(attributes);
    }
    //

    // Get single Attribute Rest Api (get id by url)
    // http://localhost:8081/api/v1/attributes/1
    @Operation(summary = "Get Attribute By ID REST API", description = "Get Attribute By ID REST API is used to get a single Attribute from the database, get id by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}")
    public Attribute getById(@PathVariable Integer id) {
        return service.getAttributeById(id);
    }
    //

    // Add new Attribute Rest Api
    // http://localhost:8081/api/v1/attributes/add
    @Operation(summary = "Crate new Attribute REST API", description = "Save new Attribute on database")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping("/add")
    public ResponseEntity<FormatResponse> add(@Valid @RequestBody Attribute p) {
         if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        repository.save(p);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Attribute created successfully!"),
                HttpStatus.CREATED);
    }
    //

    // Update Attribute Rest Api
    // http://localhost:8081/api/v1/attributes/1
    @Operation(summary = "Update Attribute REST API", description = "Update Attribute on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<FormatResponse> update(@PathVariable Integer id, @RequestBody Attribute updatedAttribute) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        return service.updateAttribute(id, updatedAttribute);
    }
    //

    // Delete Attribute Rest Api
    // http://localhost:8081/api/v1/attributes/1
    @Operation(summary = "Delete Attribute REST API", description = "Delete Attribute on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/{id}")
    public ResponseEntity<FormatResponse> delete(@PathVariable Integer id) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        service.deleteAttribute(id);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Attribute deleted successfully!"), HttpStatus.OK);

    }
    //

    

}
