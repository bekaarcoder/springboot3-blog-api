package com.blitzstriker.blogapi.service;

import com.blitzstriker.blogapi.entity.ResetToken;
import com.blitzstriker.blogapi.entity.User;
import com.blitzstriker.blogapi.payload.ForgotPasswordRequest;
import com.blitzstriker.blogapi.payload.LoginRequest;
import com.blitzstriker.blogapi.payload.PasswordRequest;
import com.blitzstriker.blogapi.payload.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);

    String login(LoginRequest request);

    User getLoggedInUser();

    ResetToken sendResetToken(ForgotPasswordRequest request);

    void resetPassword(PasswordRequest passwordRequest, String token);
}
