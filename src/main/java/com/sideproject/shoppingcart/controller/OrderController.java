package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.model.Order;
import com.sideproject.shoppingcart.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    @Operation(summary = "下單", description = "下單購物車內內容")
    public ResponseEntity<?> checkout(@RequestBody Order order) {
        return orderService.checkout(order);
    }

    @GetMapping("/{id}")
    @Operation(summary = "下單單一商品", description = "下單單一商品")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
