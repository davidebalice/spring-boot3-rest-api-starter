package com.restapi.controller;

import java.util.List;

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

import com.restapi.model.Customer;
import com.restapi.repository.CustomerRepository;
import com.restapi.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers/")
public class CustomerController {

    private final CustomerRepository repository;
    private final CustomerService service;

    public CustomerController(CustomerRepository repository, CustomerService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Customer's Rest Api", description = "This API extracts all customers")
    public Iterable<Customer> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @RouterOperation(operation = @io.swagger.v3.oas.annotations.Operation(summary = "Customer's Rest Api", description = "This API extracts one customer"))
    public Customer getById(@PathVariable Integer id) {
        return repository.findById(id).get();
    }

    @PostMapping("/add")
    public String add(@Valid @RequestBody Customer p) {
        repository.save(p);
        return "redirect:/api/customers/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("customerData") Customer c) {
        repository.save(c);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Customer updatedCustomer) {
        return service.updateCustomer(id, updatedCustomer);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        return service.deleteCustomer(id);
    }

    @GetMapping("/search")
    public List<Customer> searchCustomers(@RequestParam("keyword") String keyword) {
        List<Customer> customers = service.searchCustomers(keyword);
        return customers;
    }

}
