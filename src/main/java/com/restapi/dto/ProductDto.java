package com.restapi.dto;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String sku;
    private int idCategory;
    private int idSubcategory;
    private CategoryDto categoryDto;
    private SubcategoryDto subcategoryDto;
    private String imageUrl;
    private double price;
    private List<GalleryDto> gallery;
}