package com.restapistarter.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapistarter.model.Product;
import com.restapistarter.repository.ProductRepository;
import com.restapistarter.service.ProductService;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    @Autowired
    ProductRepository repo;

    @Autowired
    ProductService service;

    @GetMapping("/")
    public Iterable<Product> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return service.getProductById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody Product p) {
        repo.save(p);
        return "redirect:/api/prodotti/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("productDate") Product p) {
        repo.save(p);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        return service.updateProduct(id, updatedProduct);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        return service.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("keyword") String keyword) {
        List<Product> products = service.searchProducts(keyword);
        return products;
    }

    @GetMapping("/searchByCategoryId")
    public List<Product> searchProductsByCategoryId(@RequestParam int categoryId) {
        return service.searchProductsByCategoryId(categoryId);
    }

    @GetMapping("/stream-test")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
