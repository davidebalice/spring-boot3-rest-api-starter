package com.restapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.dto.GalleryDto;
import com.restapi.dto.ProductGalleryDto;
import com.restapi.model.Product;
import com.restapi.service.ProductService;

@RestController
@RequestMapping("/api/v1/products/")
public class GalleryController {

    @Autowired
    private ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/{productId}/gallery")
    public ResponseEntity<ProductGalleryDto> getProductGallery(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        List<GalleryDto> galleryDtos = product.getGallery().stream()
                .map(gallery -> new GalleryDto(gallery.getId(), gallery.getTitle(), gallery.getUrl()))
                .collect(Collectors.toList());
        ProductGalleryDto productGalleryDto = new ProductGalleryDto(product.getName(), galleryDtos);
        return ResponseEntity.ok(productGalleryDto);
    }

    @PostMapping("/{productId}/gallery/upload")
    public ResponseEntity<List<String>> uploadGalleryImages(@PathVariable int productId,
            @RequestParam("images") List<MultipartFile> multipartFiles) {
        try {
            List<String> fileDownloadUris = productService.uploadGallery(productId, multipartFiles, uploadPath);
            return ResponseEntity.ok(fileDownloadUris);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(List.of("Error uploading gallery images"));
        }
    }
}
