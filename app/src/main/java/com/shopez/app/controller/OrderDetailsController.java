package com.shopez.app.controller;

import com.shopez.app.entities.OrderInput;
import com.shopez.app.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderInput orderInput) {
    if (orderInput.getOrderProductQuantityList() == null || orderInput.getOrderProductQuantityList().isEmpty()) {
        return ResponseEntity.badRequest().body("Order product quantity list cannot be null or empty");
    }
    orderDetailsService.placeOrder(orderInput);
    return ResponseEntity.ok("Order placed successfully");
    }
}
