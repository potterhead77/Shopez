package com.shopez.app.controller;

import com.shopez.app.entities.Product;
import com.shopez.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addNewProduct")
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) {
        // Prevent setting ID manually
        if (product.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product ID must be null when creating a new product.");
        }
        Product savedProduct = productService.addNewProduct(product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savedProduct);
    }
}