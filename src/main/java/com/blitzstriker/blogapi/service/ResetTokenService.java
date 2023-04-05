package com.blitzstriker.blogapi.service;

import com.blitzstriker.blogapi.entity.ResetToken;

public interface ResetTokenService {
    ResetToken saveResetToken(ResetToken resetToken);
}
