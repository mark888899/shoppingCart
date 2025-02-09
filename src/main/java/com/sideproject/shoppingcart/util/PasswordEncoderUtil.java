package com.sideproject.shoppingcart.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoderUtil {

    /**
     * 🔹 加密密碼
     */
    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    /**
     * 🔹 驗證密碼是否匹配
     */
    public static boolean matches(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
