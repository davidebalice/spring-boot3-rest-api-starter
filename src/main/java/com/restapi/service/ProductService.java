package com.restapi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.dto.GalleryDto;
import com.restapi.dto.ProductDto;
import com.restapi.model.Product;
import com.restapi.utility.FormatResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface ProductService {
    Product addProduct(ProductDto p);

    Product getProductById(int productId);

    Product getProductBySku(String sku);

    ResponseEntity<FormatResponse> updateProduct(int id, ProductDto updatedProduct);

    ResponseEntity<FormatResponse> deleteProduct(Integer idProduct);

    List<Product> searchProducts(String keyword, Pageable pageable);

    List<Product> searchProductsByCategoryId(int categoryId, Pageable pageable);

    List<Product> getAllProducts();

    String uploadImage(int id, MultipartFile multipartFile, String uploadPath) throws IOException;

    ResponseEntity<Resource> downloadImage(String fileName, HttpServletRequest request, String uploadPath)
            throws IOException;

    ResponseEntity<Resource> downloadGallery(String fileName, HttpServletRequest request, String uploadPath)
            throws IOException;

    ProductDto addGalleryToProduct(int productId, GalleryDto galleryDto);

    List<String> uploadGallery(int productId, List<MultipartFile> multipartFiles, String uploadPath) throws IOException;
}
