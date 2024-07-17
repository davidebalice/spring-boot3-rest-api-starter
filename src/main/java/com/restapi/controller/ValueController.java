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
import com.restapi.model.Value;
import com.restapi.repository.ValueRepository;
import com.restapi.service.ValueService;
import com.restapi.utility.FormatResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs for Value Resource", description = "VALUES CRUD REST APIs - Create Value, Update Value, Get Value, Get All Values, Delete Value")
@RestController
@RequestMapping("/api/v1/values/")
public class ValueController {

    private final ValueRepository repository;
    private final ValueService service;

    public ValueController(ValueRepository repository, ValueService service) {
        this.repository = repository;
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DemoMode demoMode;

    @Operation(summary = "Get all values of one attribute", description = "Retrieve a list of all values of one attribute")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/idattr/{idattr}")
    public ResponseEntity<List<Value>> list(@PathVariable("idattr") int attributeId) {
        List<Value> values = repository.findByAttributeId(attributeId);
        List<Value> valuesDto = values.stream()
                .map(value -> modelMapper.map(value, Value.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(valuesDto);
    }

    // Get single Value Rest Api (get id by url)
    // http://localhost:8081/api/v1/values/1
    @Operation(summary = "Get Value By ID REST API", description = "Get Value By ID REST API is used to get a single Value from the database, get id by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}")
    public Value getById(@PathVariable Integer id) {
        return service.getValueById(id);
    }
    //

    // Add new Value Rest Api
    // http://localhost:8081/api/v1/values/add
    @Operation(summary = "Crate new Value REST API", description = "Save new Value on database")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping("/add")
    public ResponseEntity<FormatResponse> add(@Valid @RequestBody Value s) {
         if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
       
        repository.save(s);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Value created successfully!"),
                HttpStatus.CREATED);
    }
    //

    // Update Value Rest Api
    // http://localhost:8081/api/v1/values/1
    @Operation(summary = "Update Value REST API", description = "Update Value on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<FormatResponse> update(@PathVariable Integer id,
            @RequestBody Value updatedValue) {
                if (demoMode.isEnabled()) {
                    throw new DemoModeException();
                }
        return service.updateValue(id, updatedValue);
    }
    //

    // Delete Value Rest Api
    // http://localhost:8081/api/v1/values/1
    @Operation(summary = "Delete Value REST API", description = "Delete Value on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/{id}")
    public ResponseEntity<FormatResponse> delete(@PathVariable Integer id) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        service.deleteValue(id);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Value deleted successfully!"),
                HttpStatus.OK);

    }
    //

  

}
