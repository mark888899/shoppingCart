package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.dto.LoginRequest;
import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import com.sideproject.shoppingcart.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    private Long loggedInUserId;

    @PostMapping("/login")
    @Operation(summary = "登入", description = "輸入帳號密碼進行登入")
    public ResponseEntity<?> login(LoginRequest request) {
        ResponseEntity<?> response = authService.login(request.getUsername(), request.getPassword());
        if (response.getStatusCode().is2xxSuccessful()) {
            Optional<User> user = userRepository.findByUsername(request.getUsername());
            user.ifPresent(value -> loggedInUserId = value.getId()); // 設定當前登入的使用者 ID
        }
        return response;
    }

    public Long getLoggedInUserId() {
        return loggedInUserId;
    }

    @PostMapping("/logout")
    @Operation(summary = "登出", description = "使用 JWT Token 進行登出")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        return authService.logout(token);
    }


}
