package com.blitzstriker.blogapi.service.impl;

import com.blitzstriker.blogapi.entity.ResetToken;
import com.blitzstriker.blogapi.entity.User;
import com.blitzstriker.blogapi.exception.ApiException;
import com.blitzstriker.blogapi.repository.ResetTokenRepository;
import com.blitzstriker.blogapi.service.ResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResetTokenServiceImpl implements ResetTokenService {
    private final ResetTokenRepository resetTokenRepository;

    @Override
    public ResetToken saveResetToken(ResetToken resetToken) {
        return resetTokenRepository.save(resetToken);
    }

    @Override
    public User confirmResetToken(String token) {
        ResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Invalid token"));

        // Check if token is expired
        LocalDateTime expiredAt = resetToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token is already expired");
        }

        // Check if token is already used
        if (resetToken.getConfirmedAt() != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token already used for password reset. Please request again to reset your password");
        }

        resetToken.setConfirmedAt(LocalDateTime.now());
        resetTokenRepository.save(resetToken);

        return resetToken.getUser();
    }
}
