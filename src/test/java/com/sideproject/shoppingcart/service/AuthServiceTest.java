package com.sideproject.shoppingcart.service;

import com.sideproject.shoppingcart.dto.LoginRequest;
import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.repository.UserRepository;
import com.sideproject.shoppingcart.util.JwtUtil;
import com.sideproject.shoppingcart.util.PasswordEncoderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoderUtil passwordEncoderUtil;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        String email = "user1@example.com";
        String password = "user1pass";
        String encodedPassword = passwordEncoderUtil.encode(password);

        User mockUser = new User();
        mockUser.setUserEmail(email);
        mockUser.setPassword(encodedPassword);

        when(userRepository.findByUserEmail(email)).thenReturn(Optional.of(mockUser));

        LoginRequest request = new LoginRequest();
        request.setUserEmail(email);
        request.setPassword(password);

        ResponseEntity<?> response = authService.login(request);

        assertEquals(200, response.getStatusCode().value());
    }

//    @Test
//    public void testLogin_Failed_InvalidPassword() {
//        String email = "user1@example.com";
//        String wrongPassword = "wrongPass";
//
//        User mockUser = new User();
//        mockUser.setUserEmail(email);
//        mockUser.setPassword(passwordEncoderUtil.encode("correctPassword"));
//
//        when(userRepository.findByUserEmail(email)).thenReturn(Optional.of(mockUser));
//
//        LoginRequest request = new LoginRequest();
//        request.setUserEmail(email);
//        request.setPassword(wrongPassword);
//
//        ResponseEntity<?> response = authService.login(request);
//
//        assertEquals(401, response.getStatusCode().value());
//    }
}
