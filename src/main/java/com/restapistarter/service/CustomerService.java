package com.restapistarter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.restapistarter.model.Customer;
import com.restapistarter.repository.CategoryRepository;
import com.restapistarter.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer getCustomerById(int customerId) {
        return repository.findById(customerId)
                .orElse(null);
    }

    public ResponseEntity<String> updateCustomer(@PathVariable int id, @RequestBody Customer updateCustomer) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
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

            return new ResponseEntity<>("Client update successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String deleteCustomer(@PathVariable("id") Integer idCustomer) {
        if (idCustomer != null) {
            Optional<Customer> pOptional = repository.findById(idCustomer);
            if (pOptional.isPresent()) {
                Customer c = pOptional.get();
                repository.delete(c);
            } else {

            }
        }
        return "redirect:/customers";
    }

    public List<Customer> searchCustomers(String keyword) {
        return repository.searchCustomers(keyword);
    }

}
