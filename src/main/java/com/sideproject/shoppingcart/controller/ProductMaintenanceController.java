package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.model.Product;
import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import com.sideproject.shoppingcart.service.ProductMaintenanceService;
import com.sideproject.shoppingcart.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/maintenance")
public class ProductMaintenanceController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductMaintenanceService productMaintenanceService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    @Operation(summary = "新增商品", description = "新增商品")
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String token,@RequestBody Product product) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("用戶未登入");
        }else if(1!=user.getRole()){
            return ResponseEntity.status(403).body("登入帳號權限有誤，請再次確認");
        }
        return productMaintenanceService.addProduct(product); //TODO 前端上傳照片時的處理
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update")
    @Operation(summary = "更新商品", description = "更新商品")
    public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String token,@RequestBody Product product) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("用戶未登入");
        }else if(1!=user.getRole()){
            return ResponseEntity.status(403).body("登入帳號權限有誤，請再次確認");
        }
        return productMaintenanceService.updateProduct(product);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "刪除商品", description = "刪除商品")
    public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String token,@Parameter(description = "商品 ID", required = true, example = "1")
                                               @PathVariable("id")Long id) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("用戶未登入");
        }else if(1!=user.getRole()){
            return ResponseEntity.status(403).body("登入帳號權限有誤，請再次確認");
        }
        return productMaintenanceService.deleteProduct(id);
    }

}
