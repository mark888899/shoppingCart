package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.model.ProductCategory;
import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import com.sideproject.shoppingcart.service.ProductCategoryService;
import com.sideproject.shoppingcart.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        return ResponseEntity.ok(productCategoryService.getAllCategories());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestHeader("Authorization") String token,@RequestBody ProductCategory productCategory) {
        String userEmail = jwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = userRepository.findByUserEmail(userEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("用戶未登入");
        }else if(1!=user.getRole()){
            return ResponseEntity.status(403).body("登入帳號權限有誤，請再次確認");
        }
        return ResponseEntity.ok(productCategoryService.addCategory(productCategory));
    }
}
