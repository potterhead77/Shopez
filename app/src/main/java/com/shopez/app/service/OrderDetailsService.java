package com.shopez.app.service;

import com.shopez.app.Configuration.JwtRequestFilter;
import com.shopez.app.dao.OrderDetailsDao;
import com.shopez.app.dao.ProductDao;
import com.shopez.app.dao.UserDao;
import com.shopez.app.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {

    private static final String ORDER_PLACED = "Placed";

    @Autowired
    private OrderDetailsDao orderDetailsDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public void placeOrder(OrderInput orderInput) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        if (productQuantityList == null) {
            throw new IllegalArgumentException("Order product quantity list cannot be null");
        }
        for(OrderProductQuantity o : productQuantityList) {
            Product product = productDao.findById(o.getProductId()).orElseThrow(()->new RuntimeException("product not found"));

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).orElseThrow(()->new RuntimeException("user not found"));
            OrderDetails orderDetails = new OrderDetails(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user
            );

            orderDetailsDao.save(orderDetails);
        }
    }
}
