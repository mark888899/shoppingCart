package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.repository.UserRepository;
import com.sideproject.shoppingcart.service.OrderService;
import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/checkout")
    @Operation(summary = "下單", description = "下單購物車內內容")
    public ResponseEntity<?> checkout(@RequestHeader("Authorization") String token) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("用戶未登入");
        }

        return orderService.checkout(user.getId());
    }

    @GetMapping("/")
    @Operation(summary = "查詢訂單", description = "根據訂單 ID 查詢訂單")
    public ResponseEntity<?> getOrder(@RequestHeader("Authorization") String token) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("用戶未登入");
        }
        return orderService.getOrder(user.getId());
    }

}
