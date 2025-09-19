package com.shopez.app.service;

import com.shopez.app.dao.ProductDao;
import com.shopez.app.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productDao.findAll();
    }

    public void deleteProduct(Integer id) {
        productDao.deleteById(id);
    }

    public Product updateProduct(Integer productId, Product newProduct) {
        Product existingProduct = productDao.findById(productId).orElseThrow(()-> new NoSuchElementException("Product does not exist"+productId));
        existingProduct.setProductName(newProduct.getProductName());
        existingProduct.setProductDescription(newProduct.getProductDescription());
        existingProduct.setProductActualPrice(newProduct.getProductActualPrice());
        existingProduct.setProductDiscountedPrice(newProduct.getProductDiscountedPrice());
        // Only update images if they are provided (non-null and not empty)
        if (newProduct.getProductImages() != null && !newProduct.getProductImages().isEmpty()) {
            existingProduct.setProductImages(newProduct.getProductImages());
        }
        return productDao.save(existingProduct);
    }

    public Product getProductbyId(Integer productId) {
        return productDao.findById(productId).orElseThrow(()-> new NoSuchElementException("Product does not exist"+productId));
    }

    public List<Product> getProductDetails(Boolean isSingleProductCheckout, Integer productId) {
        if(isSingleProductCheckout){
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).orElseThrow(() -> new NoSuchElementException("Product does not exist " + productId));
            list.add(product);
            return list;
        }
        else{
            return new ArrayList<>();
        }

    }
}
