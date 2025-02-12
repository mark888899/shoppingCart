package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.dto.CartRequest;
import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import com.sideproject.shoppingcart.service.CartService;
import com.sideproject.shoppingcart.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/add")
    @Operation(summary = "加入購物車", description = "登入後根據商品ID將商品加入購物車")
    public ResponseEntity<?> addToCart(@RequestHeader("Authorization") String token,@RequestBody CartRequest request) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return cartService.addToCart(user.getId(), request.getProductId(), request.getQuantity());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/items")
    @Operation(summary = "查看購物車", description = "獲取當前登入使用者的購物車內容")
    public ResponseEntity<?> getCartItems(@RequestHeader("Authorization") String token) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        return cartService.getCartItems(user.getId());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "移除購物車商品", description = "根據商品ID刪除購物車內的特定商品")
    public ResponseEntity<?> removeFromCart(@RequestHeader("Authorization") String token,
                                            @PathVariable("id") Long id) {
        // 獲取用戶 Email
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        // 檢查用戶是否存在
        if (user == null) {
            return ResponseEntity.status(401).body("未登入用戶");
        }
        // 呼叫 cartService 來刪除購物車中的商品
        boolean removed = cartService.removeFromCart(user.getId(), id);
        if (removed) {
            return ResponseEntity.ok("商品已成功從購物車移除");
        } else {
            return ResponseEntity.status(404).body("購物車內無此商品");
        }
    }

}
