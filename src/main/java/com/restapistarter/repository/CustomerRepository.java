package com.restapistarter.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.restapistarter.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{

    void deleteById(Optional<Customer> p);

}
