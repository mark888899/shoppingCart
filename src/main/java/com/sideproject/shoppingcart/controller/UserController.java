package com.sideproject.shoppingcart.controller;

import com.sideproject.shoppingcart.model.User;
import com.sideproject.shoppingcart.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "用戶註冊", description = "用戶註冊")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }
}
