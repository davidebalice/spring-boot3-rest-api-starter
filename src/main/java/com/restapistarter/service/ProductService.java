package com.restapistarter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.restapistarter.model.Product;
import com.restapistarter.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public Product getProductById(int productId) {
        return repository.findById(productId)
                .orElse(null);
    }

    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
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

            return new ResponseEntity<>("Product update successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error update",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String deleteProduct(@PathVariable("id") Integer idProduct) {
        if (idProduct != null) {
            Optional<Product> pOptional = repository.findById(idProduct);
            if (pOptional.isPresent()) {
                Product p = pOptional.get();
                repository.delete(p);
            } else {

            }
        }
        return "redirect:/product";
    }

    public List<Product> searchProducts(String keyword) {
        return repository.searchProducts(keyword);
    }

    public List<Product> searchProductsByCategoryId(int categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        repository.findAll().forEach(products::add);
        return products;
    }
}
