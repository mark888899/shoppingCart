package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.dto.LoginRequest;
import com.sideproject.shoppingcart.dto.LoginResponse;
import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import com.sideproject.shoppingcart.util.JwtUtil;
import com.sideproject.shoppingcart.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil; // 🔹 用於 JWT 生成


    public ResponseEntity<?> login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUserEmail(request.getUserEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("帳號或密碼錯誤");
        }
        User user = userOpt.get();

        // 使用 passwordEncoder 進行安全密碼比對
        if (!PasswordEncoderUtil.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("帳號或密碼錯誤");
        }

        String token = jwtUtil.generateToken(user.getUserEmail());

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setMessage("登入成功！");
        response.setEmail(user.getUserEmail());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole()); //使用者權限
        response.setToken(token); // 🔹 回傳 JWT Token

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> logout(String token) {
        // TODO 模擬登出邏輯，實際應用應處理 JWT 或 Session
        return ResponseEntity.ok("Logout successful");
    }
}
