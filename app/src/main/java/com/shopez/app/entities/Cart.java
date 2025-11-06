package com.shopez.app.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Cart {
    public Cart() {
    }
    public Cart(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;
    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
