package com.restapi.mapper;

import com.restapi.dto.ProductDto;
import com.restapi.model.Category;
import com.restapi.model.Product;

public class ProductMapper {

    // Convert Product JPA Entity into ProductDto
    public static ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setIdCategory(product.getCategory().getId());

        return productDto;
    }

    // Convert ProductDto into Product JPA Entity
    public static Product mapToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        Category category = new Category();
        category.setId(product.getCategory().getId());
        category.setName(product.getCategory().getName());
        category.setDescription(product.getCategory().getDescription());
        product.setCategory(category);

        return product;
    }
}
