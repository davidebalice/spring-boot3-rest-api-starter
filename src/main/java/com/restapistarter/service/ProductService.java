package com.restapistarter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.restapistarter.model.Product;
import com.restapistarter.repository.ProductRepository;

@Service
public class ProductService {
  // private final RestTemplate restTemplate;

    @Autowired
    ProductRepository repo;
/* 
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
*/
    public Product getProductById(int productId) {
        return repo.findById(productId)
                .orElse(null);
    }

    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        try {
            if (!repo.existsById(id)) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }

            Product existingProduct = repo.findById(id).get();

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

            repo.save(existingProduct);

            return new ResponseEntity<>("Prodotto aggiornato con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Errore durante l'aggiornamento del prodotto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String deleteProduct(@PathVariable("id") Integer idProduct) {
        if (idProduct != null) {
            Optional<Product> pOptional = repo.findById(idProduct);
            if (pOptional.isPresent()) {
                Product p = pOptional.get();
                repo.delete(p);
            } else {

            }
        }
        return "redirect:/product";
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

    public List<Product> searchProductsByCategoryId(int categoryId) {
        return repo.findByCategoryId(categoryId);
    }
}
