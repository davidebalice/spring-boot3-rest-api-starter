package com.restapi.service;

import com.restapi.dto.ProductDto;
import com.restapi.model.Product;
import com.restapi.utility.FormatResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public interface ProductService {
    Product addProduct(ProductDto p);
    Product getProductById(int productId);
    Product getProductBySku(String sku);
    ResponseEntity<FormatResponse> updateProduct(int id, ProductDto updatedProduct);
    ResponseEntity<FormatResponse> deleteProduct(Integer idProduct);
    List<Product> searchProducts(String keyword);
    List<Product> searchProductsByCategoryId(int categoryId);
    List<Product> getAllProducts();
}
