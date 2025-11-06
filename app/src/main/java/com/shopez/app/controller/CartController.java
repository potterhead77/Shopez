package com.shopez.app.controller;

import com.shopez.app.entities.Cart;
import com.shopez.app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {

        @Autowired
        private CartService cartService;

        @PreAuthorize("hasRole('USER')")
        @GetMapping({"/addToCart/{productId}"})
        public Cart addToCart(@PathVariable(name="productId") Integer productId){
                return cartService.addToCart(productId);
        }

        @GetMapping({"/getCartDetails"})
        @PreAuthorize("hasRole('USER')")
        public List<Cart> getCartDetails(){
            return cartService.getCartDetails();
        }
}
