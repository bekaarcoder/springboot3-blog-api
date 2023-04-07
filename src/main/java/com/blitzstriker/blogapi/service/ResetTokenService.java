package com.blitzstriker.blogapi.service;

import com.blitzstriker.blogapi.entity.ResetToken;
import com.blitzstriker.blogapi.entity.User;

public interface ResetTokenService {
    ResetToken saveResetToken(ResetToken resetToken);
    User confirmResetToken(String token);
}
