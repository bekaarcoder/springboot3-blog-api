package com.blitzstriker.blogapi.service.impl;

import com.blitzstriker.blogapi.entity.ResetToken;
import com.blitzstriker.blogapi.repository.ResetTokenRepository;
import com.blitzstriker.blogapi.service.ResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetTokenServiceImpl implements ResetTokenService {
    private final ResetTokenRepository resetTokenRepository;

    @Override
    public ResetToken saveResetToken(ResetToken resetToken) {
        return resetTokenRepository.save(resetToken);
    }
}
