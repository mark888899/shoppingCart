package com.sideproject.shoppingcart.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String message;
    private String email;
    private String username;
    private String role;
    private String token; // 🔹 JWT Token
}
