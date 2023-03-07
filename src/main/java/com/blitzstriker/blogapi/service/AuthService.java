package com.blitzstriker.blogapi.service;

import com.blitzstriker.blogapi.payload.LoginRequest;
import com.blitzstriker.blogapi.payload.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);

    String login(LoginRequest request);
}
