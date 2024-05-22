package com.restapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.model.Customer;

@Service
public interface CustomerService {
    Customer getCustomerById(int customerId);

    ResponseEntity<String> updateCustomer(int id, Customer updateCustomer);

    ResponseEntity<String> deleteCustomer(Integer idCustomer);

    List<Customer> searchCustomers(String keyword);
}
