package com.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

        void deleteById(Optional<Product> p);

        // JPQL query
        @Query("SELECT p FROM Product p " +
                        "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                        "OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
        List<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);

        // Native sql query
        /*
         * @Query(value = "SELECT * FROM api_product p WHERE " +
         * "p.name LIKE CONCAT('%',:keyword, '%')" +
         * "OR p.description LIKE CONCAT('%', :keyword, '%')" +
         * "OR p.sku LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
         * List<Product> searchProducts(@Param("keyword") String keyword);
         */

        List<Product> findByCategoryId(int categoryId, Pageable pageable);

        @Query("SELECT p FROM Product p " +
                        "WHERE LOWER(p.sku) LIKE LOWER(CONCAT('%', :sku, '%')) ")
        Optional<Product> findBySku(@Param("sku") String sku);

}
