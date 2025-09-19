package com.shopez.app.controller;

import com.shopez.app.entities.ImageModel;
import com.shopez.app.entities.Product;
import com.shopez.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addNewProduct", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addOrUpdateProduct(@RequestPart("product") Product product,
                                                @RequestPart(value ="imageFile",required = false) MultipartFile[] files) {
        try {
            // Convert files to image entities if provided
            if (files != null && files.length > 0) {
                Set<ImageModel> images = uploadImage(files);
                product.setProductImages(images);
            }

            Product savedProduct;
            if (product.getProductId() != null) {
                // Update flow
                savedProduct = productService.updateProduct(product.getProductId(), product);
            } else {
                // Create flow
                savedProduct = productService.addNewProduct(product);
            }

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

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to delete product" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getProductbyId/{productId}")
    public ResponseEntity<?> getProductbyId(@PathVariable Integer productId){
        try{
            return new ResponseEntity<>(productService.getProductbyId(productId),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND );
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getProductDetails/{isSingleProductCheckout}/{productId}" )
    public ResponseEntity<List<Product>> getProductDetails(@PathVariable(name = "isSingleProductCheckout") Boolean isSingleProductCheckout, @PathVariable(name = "productId") Integer productId) {
        List<Product> products = productService.getProductDetails(isSingleProductCheckout, productId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }




}
