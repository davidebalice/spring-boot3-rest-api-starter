package com.restapi.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.dto.GalleryDto;
import com.restapi.dto.ProductDto;
import com.restapi.exception.ResourceNotFoundException;
import com.restapi.model.Category;
import com.restapi.model.Gallery;
import com.restapi.model.Product;
import com.restapi.repository.CategoryRepository;
import com.restapi.repository.GalleryRepository;
import com.restapi.repository.ProductRepository;
import com.restapi.service.ProductService;
import com.restapi.utility.FileUploadUtil;
import com.restapi.utility.FormatResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final GalleryRepository galleryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository,
            GalleryRepository galleryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.galleryRepository = galleryRepository;
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getIdCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        // product.setActive(productDto.isActive());

        return repository.save(product);
    }

    @Override
    public Product getProductById(int productId) {
        return repository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id"));
    }

    @Override
    public Product getProductBySku(String sku) {
        return repository.findBySku(sku).orElseThrow(
                () -> new ResourceNotFoundException("Product", "sku"));
    }

    @Override
    public ResponseEntity<FormatResponse> updateProduct(int id, ProductDto updatedProduct) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseEntity<FormatResponse>(new FormatResponse("Product not found"),
                        HttpStatus.NOT_FOUND);
            }

            Product existingProduct = repository.findById(id).get();

            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getDescription() != null) {
                existingProduct.setDescription(updatedProduct.getDescription());
            }
            if (updatedProduct.getIdCategory() >= 1) {
                Category category = categoryRepository.findById(updatedProduct.getIdCategory())
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                existingProduct.setCategory(category);
            }
            if (updatedProduct.getPrice() != 0.0) {
                existingProduct.setPrice(updatedProduct.getPrice());
            }
            if (updatedProduct.getImageUrl() != null) {
                existingProduct.setImageUrl(updatedProduct.getImageUrl());
            }

            repository.save(existingProduct);

            return new ResponseEntity<FormatResponse>(new FormatResponse("Product updated successfully!"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<FormatResponse>(new FormatResponse("Error updating product"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<FormatResponse> deleteProduct(Integer productId) {
        Optional<Product> pOptional = repository.findById(productId);
        if (pOptional.isPresent()) {
            Product p = pOptional.get();
            repository.delete(p);
            return new ResponseEntity<FormatResponse>(new FormatResponse("Product deleted successfully"),
                    HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Product", "id");
        }
    }

    @Override
    public List<Product> searchProducts(String keyword, Pageable pageable) {
        return repository.searchProducts(keyword, pageable);
    }

    @Override
    public List<Product> searchProductsByCategoryId(int categoryId, Pageable pageable) {
        return repository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public String uploadImage(int id, MultipartFile multipartFile, String uploadPath) throws IOException {

        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Invalid file");
        }

        String fileName = id + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = uploadPath + "/image";
        Path filePath = Paths.get(uploadDir, fileName);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        Product product = getProductById(id);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setImageUrl(fileName);
        updateProduct(id, productDto);

        return uploadDir;
    }

    @Override
    public ResponseEntity<Resource> downloadImage(String fileName, HttpServletRequest request, String uploadPath)
            throws IOException {
        Path filePath = Paths.get(uploadPath + "/image").resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Override
    public ProductDto addGalleryToProduct(int productId, GalleryDto galleryDto) {
        Optional<Product> productOpt = repository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Gallery gallery = modelMapper.map(galleryDto, Gallery.class);
            product.addImgToGallery(gallery);
            repository.save(product);
            return modelMapper.map(product, ProductDto.class);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public List<String> uploadGallery(int productId, List<MultipartFile> multipartFiles, String uploadPath)
            throws IOException {
        List<String> fileDownloadUris = new ArrayList<>();

        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        for (MultipartFile multipartFile : multipartFiles) {
            Gallery gallery = new Gallery();
            gallery.setTitle(multipartFile.getOriginalFilename());

            String fileDownloadUri = saveGalleryImage(productId, product, gallery, multipartFile, uploadPath);

            fileDownloadUris.add(fileDownloadUri);
        }

        return fileDownloadUris;
    }

    private String saveGalleryImage(int productId, Product product, Gallery gallery, MultipartFile multipartFile,
            String uploadPath) throws IOException {

        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Invalid file");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String fileName = timestamp + "_" + StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadDir = uploadPath + "/gallery";
        Path filePath = Paths.get(uploadDir, fileName);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        Product prod = getProductById(productId);
        ProductDto productDto = modelMapper.map(prod, ProductDto.class);
        productDto.setImageUrl(fileName);
        updateProduct(productId, productDto);

        gallery.setProduct(product);
        gallery.setUrl(fileName);
        galleryRepository.save(gallery);

        return uploadDir + "\\" + fileName;
    }
}
