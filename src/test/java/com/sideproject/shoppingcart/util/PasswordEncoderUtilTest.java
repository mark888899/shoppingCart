package com.sideproject.shoppingcart.util;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasswordEncoderUtilTest {

    @Test
    public void testEncode() {
        String rawPassword = "user1pass";
        String encodedPassword = PasswordEncoderUtil.encode(rawPassword);
        System.out.println(encodedPassword);
        assertNotNull(encodedPassword);
        assertTrue(BCrypt.checkpw(rawPassword, encodedPassword));
    }
}
