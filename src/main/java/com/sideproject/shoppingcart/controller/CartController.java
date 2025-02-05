package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.dto.CartRequest;
import com.sideproject.shoppingcart.model.Cart;
import com.sideproject.shoppingcart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthController authController; // 取得當前登入的使用者 ID

    @PostMapping("/add")
    @Operation(summary = "加入購物車", description = "登入後根據商品ID將商品加入購物車")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request) {
        Long userId = authController.getLoggedInUserId();
        if (userId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return cartService.addToCart(userId, request.getProductId(), request.getQuantity());
    }

    @GetMapping("/items")
    @Operation(summary = "查看購物車", description = "獲取當前登入使用者的購物車內容")
    public ResponseEntity<?> getCartItems() {
        Long userId = authController.getLoggedInUserId();
        if (userId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return cartService.getCartItems(userId);
    }

    @DeleteMapping("/remove/{productId}")
    @Operation(summary = "移除購物車商品", description = "根據商品ID刪除購物車內的特定商品")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {
        Long userId = authController.getLoggedInUserId();
        if (userId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return cartService.removeFromCart(userId, productId);
    }
}
