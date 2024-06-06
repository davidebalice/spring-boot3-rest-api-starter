package com.restapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.dto.ProductDto;
import com.restapi.mapper.ProductMapper;
import com.restapi.model.Product;
import com.restapi.repository.ProductRepository;
import com.restapi.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CRUD REST APIs for Product Resource", description = "PRODUCTS CRUD REST APIs - Create Product, Update Product, Get Product, Get All Products, Delete Product")
@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {

    private final ProductRepository repository;
    private final ProductService service;

    public ProductController(ProductRepository repository, ProductService service) {
        this.repository = repository;
        this.service = service;
    }

    // Get all Products Rest Api
    // http://localhost:8081/api/v1/products
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/")
    public ResponseEntity<Iterable<ProductDto>> list() {
        Iterable<Product> products = repository.findAll();
        List<ProductDto> productsDto = new ArrayList<>();
        for (Product product : products) {
            productsDto.add(ProductMapper.mapToProductDto(product));
        }
        return ResponseEntity.ok(productsDto);
    }
    //

    // Get single Product Rest Api by Id (get id by url)
    // http://localhost:8081/api/v1/products/1
    @Operation(summary = "Get Product By ID REST API", description = "Get Product By ID REST API is used to get a single Product from the database, get id by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Integer id) {
        Product product = service.getProductById(id);
        if (product != null) {
            ProductDto productDto = ProductMapper.mapToProductDto(product);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //

    // Get single Product Rest Api (get id by querystring)
    // http://localhost:8081/api/v1/product?id=1
    @Operation(summary = "Get Product By ID REST API", description = "Get Product By ID REST API is used to get a single Product from the database, get id by querystring")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/product")
    public Product getByIdQs(@RequestParam Integer id) {
        return service.getProductById(id);
    }
    //

    // Add new Product Rest Api
    // http://localhost:8081/api/v1/products/add
    @Operation(summary = "Crate new  Product REST API", description = "Save new Product on database")
    @ApiResponse(responseCode = "201", description = "HTTP Status 201 Created")
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Product p) {
        repository.save(p);
        return ResponseEntity.ok("Product added successfully");
    }
    //

    // Update Product Rest Api
    // http://localhost:8081/api/v1/products/1
    @Operation(summary = "Update Product REST API", description = "Update Product on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        return service.updateProduct(id, updatedProduct);
    }
    //

    // Delete Product Rest Api
    // http://localhost:8081/api/v1/products/1
    @Operation(summary = "Delete Product REST API", description = "Delete Product on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
    //

    // Get single Product Rest Api by Sku
    // http://localhost:8081/api/v1/products/sku/11abc4g41125
    @Operation(summary = "Get Product By Sku REST API", description = "Get Product By Sku REST API is used to get a single Product from the database, get Sku by url")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDto> getBySku(@PathVariable String sku) {
        Product product = service.getProductBySku(sku);
        if (product != null) {
            ProductDto productDto = ProductMapper.mapToProductDto(product);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //

    // Search Product Rest Api
    // http://localhost:8081/api/v1/products/search
    @Operation(summary = "Search Product REST API", description = "Search Product on database by filter")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam("keyword") String keyword) {
        List<Product> products = service.searchProducts(keyword);
        List<ProductDto> productsDto = products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsDto);
    }
    //

    // Search Product by Category Rest Api
    // http://localhost:8081/api/v1/products/searchByCategoryId
    @Operation(summary = "Search Product by Category Api REST API", description = "Search Product by Category Api on database by id")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/searchByCategoryId")
    public ResponseEntity<List<ProductDto>> searchProductsByCategoryId(@RequestParam int categoryId) {
        List<Product> products = service.searchProductsByCategoryId(categoryId);
        List<ProductDto> productsDto = products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsDto);
    }
    //

    // Get all products Rest Api and obtain a stream data
    // http://localhost:8081/api/v1/products/stream-test
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/stream-test")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        List<ProductDto> productsDto = products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsDto);
    }
    //

}
