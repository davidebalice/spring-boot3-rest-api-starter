package com.restapi.controller;

import java.util.List;

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

import com.restapi.model.Customer;
import com.restapi.repository.CustomerRepository;
import com.restapi.service.CustomerService;
import com.restapi.utility.FormatResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
        name = "CRUD REST APIs for Customer Resource",
        description = "CUSTOMERS CRUD REST APIs - Create Customer, Update Customer, Get Customer, Get All Customers, Delete Customer"
)
@RestController
@RequestMapping("/api/v1/customers/")
public class CustomerController {

    private final CustomerRepository repository;
    private final CustomerService service;

    public CustomerController(CustomerRepository repository, CustomerService service) {
        this.repository = repository;
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    //Get all Customers Rest Api
    //http://localhost:8081/api/v1/customers
    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers")
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/")
    public Iterable<Customer> list() {
        return repository.findAll();
    }
    //


    //Get single Customer Rest Api (get id by url)
    //http://localhost:8081/api/v1/customers/1
    @Operation(
        summary = "Get Customer By ID REST API",
        description = "Get Customer By ID REST API is used to get a single Customer from the database, get id by url"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public Customer getById(@PathVariable Integer id) {
        return repository.findById(id).get();
    }
    //


    //Add new Customer Rest Api
    //http://localhost:8081/api/v1/customers/add
    @Operation(
        summary = "Crate new Customer REST API",
        description = "Save new Customer on database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 Created"
    )
    @PostMapping("/add")
    public ResponseEntity<FormatResponse> add(@Valid @RequestBody Customer p) {
        repository.save(p);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Customer addedd successfully!"), HttpStatus.CREATED);
    }
    //


    //Update Customer Rest Api
    //http://localhost:8081/api/v1/customers/1
    @Operation(
        summary = "Update Customer REST API",
        description = "Update Customer on database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<FormatResponse> update(@PathVariable Integer id, @RequestBody Customer updatedCustomer) {
        return service.updateCustomer(id, updatedCustomer);
    }
    //


    //Delete Customer Rest Api
    //http://localhost:8081/api/v1/customers/1
    @Operation(
        summary = "Delete Customer REST API",
        description = "Delete Customer on database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<FormatResponse> delete(@PathVariable Integer id) {
        service.deleteCustomer(id);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Customer deleted successfully!"), HttpStatus.OK);
    }
    //

    
    //Search Customer Rest Api
    //http://localhost:8081/api/v1/customers/search
    @Operation(
        summary = "Search Customer REST API",
        description = "Search Customer on database by filter"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/search")
    public List<Customer> searchCustomers(@RequestParam("keyword") String keyword) {
        List<Customer> customers = service.searchCustomers(keyword);
        return customers;
    }
    //

}
