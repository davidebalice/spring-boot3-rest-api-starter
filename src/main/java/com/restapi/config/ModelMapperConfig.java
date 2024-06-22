package com.restapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restapi.dto.ProductDto;
import com.restapi.model.Product;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Product, ProductDto> typeMap = modelMapper.createTypeMap(Product.class, ProductDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getCategory(), ProductDto::setCategoryDto));
        return modelMapper;
    }
}
