package com.restapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.config.DemoMode;
import com.restapi.dto.ProductAttributeDto;
import com.restapi.dto.ProductDto;
import com.restapi.exception.DemoModeException;
import com.restapi.model.Product;
import com.restapi.model.ProductAttribute;
import com.restapi.repository.ProductAttributeRepository;
import com.restapi.repository.ProductRepository;
import com.restapi.security.JwtService;
import com.restapi.service.ProductAttributeService;
import com.restapi.service.ProductService;
import com.restapi.utility.FormatResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "CRUD REST APIs for Product Resource", description = "PRODUCTS CRUD REST APIs - Create Product, Update Product, Get Product, Get All Products, Delete Product")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {

    private final ProductRepository repository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeService productAttributeService;
    private final ProductService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DemoMode demoMode;

    @Value("${upload.path}")
    private String uploadPath;

    public ProductController(ProductRepository repository, ProductAttributeRepository productAttributeRepository,
            ProductService service, ProductAttributeService productAttributeService) {
        this.repository = repository;
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeService = productAttributeService;
        this.service = service;
    }

    // Get all Products Rest Api
    // http://localhost:8081/api/v1/products
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Iterable<Product> products = repository.findAll(pageable);
        List<ProductDto> productsDto = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productsDto.add(productDto);
        }

        Page<Product> productsPage = repository.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("products", productsDto);
        response.put("totalItems", productsPage.getTotalElements());

        return ResponseEntity.ok(response);
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
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
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
    public ResponseEntity<FormatResponse> add(@RequestBody ProductDto p) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        service.addProduct(p);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Product added successfully!"),
                HttpStatus.CREATED);
    }
    //

    // Update Product Rest Api
    // http://localhost:8081/api/v1/products/1
    @Operation(summary = "Update Product REST API", description = "Update Product on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PatchMapping("/{id}")
    public ResponseEntity<FormatResponse> update(@PathVariable Integer id, @RequestBody ProductDto updatedProduct) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        if (!repository.existsById(id)) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Product not found"), HttpStatus.NOT_FOUND);
        }
        return service.updateProduct(id, updatedProduct);
    }
    //

    // Delete Product Rest Api
    // http://localhost:8081/api/v1/products/1
    @Operation(summary = "Delete Product REST API", description = "Delete Product on database")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @DeleteMapping("/{id}")
    public ResponseEntity<FormatResponse> delete(@PathVariable Integer id) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        service.deleteProduct(id);
        return new ResponseEntity<FormatResponse>(new FormatResponse("Product deleted successfully!"), HttpStatus.OK);
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
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
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
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = service.searchProducts(keyword, pageable);
        List<ProductDto> productsDto = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("products", productsDto);
        response.put("totalItems", products.size());

        return ResponseEntity.ok(response);
    }
    //

    // Search Product by Category Rest Api
    // http://localhost:8081/api/v1/products/searchByCategoryId
    @Operation(summary = "Search Product by Category Api REST API", description = "Search Product by Category Api on database by id")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/searchByCategoryId")
    public ResponseEntity<Map<String, Object>> searchProductsByCategoryId(@RequestParam int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = service.searchProductsByCategoryId(categoryId, pageable);
        List<ProductDto> productsDto = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("products", productsDto);
        response.put("totalItems", products.size());

        return ResponseEntity.ok(response);
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
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsDto);
    }
    //

    @Operation(summary = "Upload product image", description = "Upload photo of product")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<FormatResponse> uploadImage(@PathVariable int id,
            @RequestParam("image") MultipartFile multipartFile) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        try {
            String fileDownloadUri = service.uploadImage(id, multipartFile, uploadPath);
            return new ResponseEntity<>(new FormatResponse(fileDownloadUri), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new FormatResponse("Error uploading image"), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Download product image", description = "Download photo of product")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        try {
            return service.downloadImage(fileName, request, uploadPath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Set attribute and value to product", description = "Set attribute and value to product")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PostMapping("/{id}/attributes")
    public ResponseEntity<FormatResponse> addAttributeToProduct(@PathVariable int id,
            @RequestBody ProductAttribute productAttribute) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();

        productAttribute.setProduct(product);

        Optional<ProductAttribute> existingAttribute = productAttributeRepository
                .findByProductAndAttributeAndAttributeValue(
                        product, productAttribute.getAttribute(), productAttribute.getAttributeValue());

        if (existingAttribute.isPresent()) {
            return new ResponseEntity<FormatResponse>(
                    new FormatResponse("Attribute combination already exists for this product."), HttpStatus.CONFLICT);
        }

        productAttributeRepository.save(productAttribute);

        return new ResponseEntity<FormatResponse>(new FormatResponse("Attribute assigned to product"), HttpStatus.OK);
    }

    @Operation(summary = "Set attribute and value to product", description = "Set attribute and value to product")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @PostMapping("/{id}/remove-attributes")
    public ResponseEntity<FormatResponse> removeAttributeToProduct(@PathVariable int id,
            @RequestBody ProductAttribute productAttribute) {
        if (demoMode.isEnabled()) {
            throw new DemoModeException();
        }
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();

        productAttribute.setProduct(product);

        Optional<ProductAttribute> existingAttribute = productAttributeRepository
                .findByProductAndAttributeAndAttributeValue(
                        product, productAttribute.getAttribute(), productAttribute.getAttributeValue());

        if (!existingAttribute.isPresent()) {
            return new ResponseEntity<>(new FormatResponse("Attribute combination does not exist for this product."),
                    HttpStatus.NOT_FOUND);
        }

        productAttributeRepository.delete(existingAttribute.get());

        return new ResponseEntity<>(new FormatResponse("Attribute removed from product"), HttpStatus.OK);
    }

    @Operation(summary = "Get attributes and values of a product", description = "Get attributes and values of a product")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 SUCCESS")
    @GetMapping("/{id}/setted-attributes-value")
    public ResponseEntity<List<ProductAttributeDto>> getSettedAttributesAndValues(@PathVariable int id) {
        List<ProductAttribute> attributes = productAttributeService.getAttributesByProductId(id);

        List<ProductAttributeDto> attributeDtos = attributes.stream()
                .map(pa -> new ProductAttributeDto(pa.getAttribute(), pa.getAttributeValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(attributeDtos);
    }
}
