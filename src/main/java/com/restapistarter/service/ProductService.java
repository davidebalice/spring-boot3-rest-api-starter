package com.restapistarter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.restapistarter.model.Product;

@Service
public class ProductService {
    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public Product getProductById(Long productId) {
        String url = "http://localhost:8081/products/{productId}";
        return restTemplate.getForObject(url, Product.class, productId);
    }
    
}



