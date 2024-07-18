package com.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restapi.model.Attribute;
import com.restapi.model.Product;
import com.restapi.model.ProductAttribute;
import com.restapi.model.Value;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    List<ProductAttribute> findByProduct(Product product);

    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.product.id = :productId")
    List<ProductAttribute> findAttributesByProductId(@Param("productId") int productId);

    Optional<ProductAttribute> findByProductAndAttributeAndAttributeValue(Product product, Attribute attribute,
            Value attributeValue);
}