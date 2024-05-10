package com.restapistarter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapistarter.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    void deleteById(Optional<Product> p);

}
