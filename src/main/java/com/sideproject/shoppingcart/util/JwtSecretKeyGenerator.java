package com.sideproject.shoppingcart.util;

import java.util.Base64;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) {
        byte[] keyBytes = new byte[32]; // 256-bit key
        new java.security.SecureRandom().nextBytes(keyBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Base64 Encoded Secret Key: " + base64Key);
    }
}
