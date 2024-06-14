package com.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restapi.dto.ProductDto;
import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.Category;
import com.restapi.model.Product;
import com.restapi.repository.CategoryRepository;
import com.restapi.repository.ProductRepository;
import com.restapi.service.ProductService;
import com.restapi.utility.FormatResponse;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        // product.setImageUrl(productDto.getImageUrl());
        // product.setActive(productDto.isActive());

        return repository.save(product);
    }

    @Override
    public Product getProductById(int productId) {
        return repository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id"));
    }

    @Override
    public Product getProductBySku(String sku) {
        return repository.findBySku(sku).orElseThrow(
                () -> new ResourceNotFoundException("Product", "sku"));
    }

    @Override
    public ResponseEntity<FormatResponse> updateProduct(int id, ProductDto updatedProduct) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<FormatResponse>(new FormatResponse("Product not found"), HttpStatus.NOT_FOUND);
            }

            Product existingProduct = repository.findById(id).get();

            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getIdCategory() >= 1) {
                Category category = categoryRepository.findById(updatedProduct.getIdCategory())
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                existingProduct.setCategory(category);
            }
            if (updatedProduct.getPrice() != 0.0) {
                existingProduct.setPrice(updatedProduct.getPrice());
            }

            repository.save(existingProduct);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Product updated successfully!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating product"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<FormatResponse> deleteProduct(Integer productId) {
        Optional<Product> pOptional = repository.findById(productId);
        if (pOptional.isPresent()) {
            Product p = pOptional.get();
            repository.delete(p);
            return new ResponseEntity<FormatResponse>(new FormatResponse("Product deleted successfully"), HttpStatus.OK);
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
