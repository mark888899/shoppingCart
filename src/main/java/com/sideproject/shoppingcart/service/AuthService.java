package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> login(String userEmail, String password) {
        Optional<User> foundUser = userRepository.findByUserEmail(userEmail);

        if (foundUser.isPresent() && Objects.equals(password, foundUser.get().getPassword())) {
            User user = foundUser.get();

            // 建立回應的 JSON 物件
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", user.getId());
            responseData.put("username", user.getUsername());
            responseData.put("email", user.getUserEmail());
            responseData.put("message", "歡迎登入 " + user.getUsername());

            return ResponseEntity.ok(responseData);
        }
        return ResponseEntity.status(401).body("帳號或密碼錯誤");
    }

    public ResponseEntity<?> logout(String token) {
        // TODO 模擬登出邏輯，實際應用應處理 JWT 或 Session
        return ResponseEntity.ok("Logout successful");
    }
}
