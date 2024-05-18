package com.restapi.mapper;

import com.restapi.dto.CategoryDto;
import com.restapi.dto.ProductDto;
import com.restapi.model.Product;

public class ProductMapper {

    // Convert Product JPA Entity into ProductDto
    public static ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());

        // Convert Category entity to CategoryDto
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setDescription(product.getCategory().getDescription());

        productDto.setCategory(categoryDto);

        return productDto;
    }

    // Convert ProductDto into Product JPA Entity
    public static Product mapToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setDescription(product.getCategory().getDescription());
        productDto.setCategory(categoryDto);

        return product;
    }
}
