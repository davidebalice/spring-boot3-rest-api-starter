package com.restapi.service;

import com.restapi.model.Product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public interface ProductService {
    Product getProductById(int productId);
    ResponseEntity<String> updateProduct(int id, Product updatedProduct);
    ResponseEntity<String> deleteProduct(Integer idProduct);
    List<Product> searchProducts(String keyword);
    List<Product> searchProductsByCategoryId(int categoryId);
    List<Product> getAllProducts();
}
