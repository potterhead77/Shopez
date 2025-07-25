package com.shopez.app.controller;

import com.shopez.app.entities.ImageModel;
import com.shopez.app.entities.Product;
import com.shopez.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addNewProduct", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addNewProduct(@RequestPart("product") Product product,
                                           @RequestPart("imageFile") MultipartFile[] files) {
        try {
            // Validate input: ID must not be set manually
            if (product.getProductId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Product ID must be null when creating a new product.");
            }

            // Convert files to image entities
            Set<ImageModel> images = uploadImage(files);
            product.setProductImages(images); // Set images on product

            // Save product with images (cascade handles images)
            Product savedProduct = productService.addNewProduct(product);

            return ResponseEntity.status(HttpStatus.OK).body(savedProduct);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while processing images: " + e.getMessage());
        }
    }

    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();

        for (MultipartFile file : multipartFiles) {
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }
}
