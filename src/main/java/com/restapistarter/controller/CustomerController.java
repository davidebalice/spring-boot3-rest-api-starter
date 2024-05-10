package com.restapistarter.controller;

import java.util.Optional;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapistarter.model.Customer;
import com.restapistarter.repository.CustomerRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers/")
public class CustomerController {

    @Autowired
    CustomerRepository repo;

    @GetMapping("/")
    @Operation(summary = "Customer's Rest Api", description = "This API extracts all customers")
    public Iterable<Customer> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @RouterOperation(operation = @io.swagger.v3.oas.annotations.Operation(summary = "Api Cliente", description = "Questa API estrae un singolo cliente"))
    public Customer getById(@PathVariable Integer id) {
        return repo.findById(id).get();
    }

    @PostMapping("/add")
    public String add(@Valid @RequestBody Customer p) {
        repo.save(p);
        return "redirect:/api/customers/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("datiCustomer") Customer p) {
        repo.save(p);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id, @RequestBody Customer updateCustomer) {
        try {
            if (!repo.existsById(id)) {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }

            Customer existingCustomer = repo.findById(id).get();

            if (updateCustomer.getName() != null) {
                existingCustomer.setName(updateCustomer.getName());
            }
            if (updateCustomer.getSurname() != null) {
                existingCustomer.setSurname(updateCustomer.getSurname());
            }
            if (updateCustomer.getEmail() != null) {
                existingCustomer.setEmail(updateCustomer.getEmail());
            }

            repo.save(existingCustomer);

            return new ResponseEntity<>("Client update successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer idCliente) {
        if (idCliente != null) {
            Optional<Customer> pOptional = repo.findById(idCliente);
            if (pOptional.isPresent()) {
                Customer c = pOptional.get();
                repo.delete(c);
            } else {

            }
        }
        return "redirect:/api/customers";
    }

}
