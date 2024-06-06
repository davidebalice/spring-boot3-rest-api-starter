package com.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.Product;
import com.restapi.repository.ProductRepository;
import com.restapi.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product getProductById(int productId) {
        return repository.findById(productId).orElseThrow(
            () -> new ResourceNotFoundException("Product", "id")
        );
    }

    @Override
    public Product getProductBySku(String sku) {
        return repository.findBySku(sku).orElseThrow(
            () -> new ResourceNotFoundException("Product", "sku")
        );
    }

    @Override
    public ResponseEntity<String> updateProduct(int id, Product updatedProduct) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }

            Product existingProduct = repository.findById(id).get();

            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getCategory() != null) {
                existingProduct.setCategory(updatedProduct.getCategory());
            }
            if (updatedProduct.getPrice() != 0.0) {
                existingProduct.setPrice(updatedProduct.getPrice());
            }

            repository.save(existingProduct);

            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer productId) {
        Optional<Product> pOptional = repository.findById(productId);
        if (pOptional.isPresent()) {
            Product p = pOptional.get();
            repository.delete(p);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Product", "id");
        }
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return repository.searchProducts(keyword);
    }

    @Override
    public List<Product> searchProductsByCategoryId(int categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}
