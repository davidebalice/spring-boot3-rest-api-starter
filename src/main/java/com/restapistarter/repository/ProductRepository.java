package com.restapistarter.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.restapistarter.model.Product;


public interface ProductRepository extends CrudRepository<Product,Integer>{

    void deleteById(Optional<Product> p);

}
