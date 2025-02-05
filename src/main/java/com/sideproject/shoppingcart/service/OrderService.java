package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    private final Map<Long, Order> orders = new HashMap<>();
    private long orderIdCounter = 1;

    public ResponseEntity<?> checkout(Order order) {
        order.setId(orderIdCounter++);
        orders.put(order.getId(), order);
        return ResponseEntity.ok("Order placed successfully. Order ID: " + order.getId());
    }

    public ResponseEntity<?> getOrder(Long id) {
        if (orders.containsKey(id)) {
            return ResponseEntity.ok(orders.get(id));
        }
        return ResponseEntity.status(404).body("Order not found");
    }
}
