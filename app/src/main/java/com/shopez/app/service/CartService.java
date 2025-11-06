package com.shopez.app.service;

import com.shopez.app.Configuration.JwtRequestFilter;
import com.shopez.app.dao.CartDao;
import com.shopez.app.dao.ProductDao;
import com.shopez.app.dao.UserDao;
import com.shopez.app.entities.Cart;
import com.shopez.app.entities.Product;
import com.shopez.app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public Cart addToCart(Integer productId) {
        Product product = productDao.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        String username  = JwtRequestFilter.CURRENT_USER;

        User user = null;
        if(username != null){
            user = userDao.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
        }


        if(product!=null && user!=null){
            Cart cart = new Cart(user,product);
            return cartDao.save(cart);
        }

        return null;
    }

    public List<Cart> getCartDetails( ) {

        String  username  = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
        return cartDao.findByUser(user);
    }
}
