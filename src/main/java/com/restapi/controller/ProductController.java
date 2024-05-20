package com.restapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {

    private final ProductRepository repository;
    private final ProductService service;

    public ProductController(ProductRepository repository, ProductService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<ProductDto>> list() {
        Iterable<Product> products = repository.findAll();
        List<ProductDto> productsDto = new ArrayList<>();
        for (Product product : products) {
            productsDto.add(ProductMapper.mapToProductDto(product));
        }
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/alldata")
    public ResponseEntity<Iterable<Product>> listAll() {
        Iterable<Product> products = repository.findAll();
        return ResponseEntity.ok(products);
    }

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

    @GetMapping("/product")
    public Product getByIdQs(@RequestParam Integer id) {
        return service.getProductById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Product p) {
        repository.save(p);
        return ResponseEntity.ok("Product added successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody Product updatedProduct) {
        if (!repository.existsById(updatedProduct.getId())) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        repository.save(updatedProduct);
        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        return service.updateProduct(id, updatedProduct);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam("keyword") String keyword) {
        List<Product> products = service.searchProducts(keyword);
        List<ProductDto> productsDto = products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/searchByCategoryId")
    public ResponseEntity<List<ProductDto>> searchProductsByCategoryId(@RequestParam int categoryId) {
        List<Product> products = service.searchProductsByCategoryId(categoryId);
        List<ProductDto> productsDto = products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsDto);
    }

    @GetMapping("/stream-test")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        List<ProductDto> productsDto = products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsDto);
    }
}
