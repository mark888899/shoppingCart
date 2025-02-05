package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> login(String username, String password) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isPresent() && Objects.equals(password,foundUser.get().getPassword()) ) {
            return ResponseEntity.ok("歡迎登入" + foundUser.get().getUsername());
        }
        return ResponseEntity.status(401).body("帳號或密碼錯誤");
    }

    public ResponseEntity<?> logout(String token) {
        // TODO 模擬登出邏輯，實際應用應處理 JWT 或 Session
        return ResponseEntity.ok("Logout successful");
    }
}
