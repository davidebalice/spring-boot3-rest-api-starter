package com.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.Customer;
import com.restapi.repository.CustomerRepository;
import com.restapi.service.CustomerService;
import com.restapi.utility.FormatResponse;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer getCustomerById(int customerId) {
        return repository.findById(customerId).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "id")
        );
    }

    @Override
    public ResponseEntity<FormatResponse> updateCustomer(int id, Customer updateCustomer) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<FormatResponse>(new FormatResponse("Customer not found"),
                    HttpStatus.NOT_FOUND);
            }

            Customer existingCustomer = repository.findById(id).get();

            if (updateCustomer.getName() != null) {
                existingCustomer.setName(updateCustomer.getName());
            }
            if (updateCustomer.getSurname() != null) {
                existingCustomer.setSurname(updateCustomer.getSurname());
            }
            if (updateCustomer.getEmail() != null) {
                existingCustomer.setEmail(updateCustomer.getEmail());
            }

            repository.save(existingCustomer);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Customer updated successfully!"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updated customer!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteCustomer(Integer customerId) {
        Optional<Customer> pOptional = repository.findById(customerId);
        if (pOptional.isPresent()) {
            Customer c = pOptional.get();
            repository.delete(c);
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Customer", "id");
        }
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {
        return repository.searchCustomers(keyword);
    }
}
