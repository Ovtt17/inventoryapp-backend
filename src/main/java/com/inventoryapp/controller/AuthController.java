package com.inventoryapp.controller;

import com.inventoryapp.dto.LoginRequest;
import com.inventoryapp.dto.RegisterRequest;
import com.inventoryapp.dto.UserResponse;
import com.inventoryapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody @Valid final RegisterRequest request
    ) {
        UserResponse user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/google")
    public ResponseEntity<UserResponse> authenticateWithGoogle(
            @RequestBody String token
    ) {
        UserResponse user = authService.authenticateWithGoogle(token);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @RequestBody @Valid final LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
