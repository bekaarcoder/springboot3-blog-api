package com.blitzstriker.blogapi.controller;

import com.blitzstriker.blogapi.entity.ResetToken;
import com.blitzstriker.blogapi.payload.*;
import com.blitzstriker.blogapi.service.AuthService;
import com.blitzstriker.blogapi.service.ResetTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/forgetpassword")
    public ResponseEntity<ResetToken> forgetPassword(@RequestBody ForgotPasswordRequest request) {
        return new ResponseEntity<>(authService.sendResetToken(request), HttpStatus.CREATED);
    }

    @PostMapping("/resetpassword/confirm")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordRequest request) {
        authService.resetPassword(request, token);
        return new ResponseEntity<>("Password reset successful", HttpStatus.OK);
    }
}
