package com.restapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.model.ProductAttribute;
import com.restapi.repository.ProductAttributeRepository;

@Service
public class ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    public List<ProductAttribute> getAttributesByProductId(int productId) {
        return productAttributeRepository.findAttributesByProductId(productId);
    }
}
