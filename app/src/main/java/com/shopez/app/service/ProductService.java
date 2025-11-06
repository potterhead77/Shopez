package com.shopez.app.service;

import com.shopez.app.Configuration.JwtRequestFilter;
import com.shopez.app.dao.CartDao;
import com.shopez.app.dao.ProductDao;
import com.shopez.app.dao.UserDao;
import com.shopez.app.entities.Cart;
import com.shopez.app.entities.Product;
import com.shopez.app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao UserDao;

    @Autowired
    private CartDao cartDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        if(searchKey.isEmpty()){
            return (List<Product>) productDao.findAll(pageable);
        }
        else{
            return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey,searchKey,pageable);
        }

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
        if(isSingleProductCheckout && productId!=0){
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).orElseThrow(() -> new NoSuchElementException("Product does not exist " + productId));
            list.add(product);
            return list;
        }
        else{
            String userName = JwtRequestFilter.CURRENT_USER;
            User user = UserDao.findById(userName).orElseThrow(() -> new NoSuchElementException("User does not exist " + userName));

            List<Cart>carts = cartDao.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
        }

    }
}
