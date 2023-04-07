package com.blitzstriker.blogapi.service.impl;

import com.blitzstriker.blogapi.entity.ResetToken;
import com.blitzstriker.blogapi.entity.Role;
import com.blitzstriker.blogapi.entity.User;
import com.blitzstriker.blogapi.exception.ApiException;
import com.blitzstriker.blogapi.payload.ForgotPasswordRequest;
import com.blitzstriker.blogapi.payload.LoginRequest;
import com.blitzstriker.blogapi.payload.PasswordRequest;
import com.blitzstriker.blogapi.payload.RegisterRequest;
import com.blitzstriker.blogapi.repository.RoleRepository;
import com.blitzstriker.blogapi.repository.UserRepository;
import com.blitzstriker.blogapi.security.JwtTokenProvider;
import com.blitzstriker.blogapi.service.AuthService;
import com.blitzstriker.blogapi.service.EmailService;
import com.blitzstriker.blogapi.service.ResetTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResetTokenService resetTokenService;
    private final EmailService emailService;

    @Override
    public String register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User is registered successfully";
    }

    @Override
    public String login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsernameOrEmail(username, username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email or username: " + username)
        );
    }

    @Override
    public ResetToken sendResetToken(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "User does not exist with the email"));

        String token = UUID.randomUUID().toString();
        ResetToken resetToken = new ResetToken();
        resetToken.setToken(token);
        resetToken.setCreatedAt(LocalDateTime.now());
        resetToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        resetToken.setUser(user);

        // Send Email
        String link = "http://localhost:8080/api/auth/resetpassword/confirm?token=" + token;
        emailService.send(user.getEmail(), buildEmail(user.getUsername(), link));

        return resetTokenService.saveResetToken(resetToken);
    }

    @Override
    @Transactional
    public void resetPassword(PasswordRequest passwordRequest, String token) {
        User user = resetTokenService.confirmResetToken(token);
        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Password does not match");
        }

        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
    }

    private String buildEmail(String name, String link) {
        String htmlString = """
                <h3>Hi %1$s</h3>
                <p>You have requested to reset your password. Please click on the below link to reset your password.</p>
                <a href="%2$s">%2$s</a>
                <p>If you have not requested for the password reset, please ignore.</p>
                """;
        return String.format(htmlString, name, link);
    }
}
