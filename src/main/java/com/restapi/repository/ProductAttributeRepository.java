package com.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.model.Attribute;
import com.restapi.model.AttributeValue;
import com.restapi.model.Product;
import com.restapi.model.ProductAttribute;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    List<ProductAttribute> findByProduct(Product product);
    Optional<ProductAttribute> findByProductAndAttributeAndAttributeValue(Product product, Attribute attribute, AttributeValue attributeValue);
}