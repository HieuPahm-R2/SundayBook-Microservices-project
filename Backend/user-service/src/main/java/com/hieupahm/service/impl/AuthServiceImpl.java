package com.hieupahm.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hieupahm.model.User;
import com.hieupahm.payload.request.SignupDTO;
import com.hieupahm.payload.response.AuthResponse;
import com.hieupahm.payload.response.TokenResponse;
import com.hieupahm.repository.UserRepository;
import com.hieupahm.service.AuthService;
import com.hieupahm.service.KeycloakService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(
                username,
                password,
                "password",
                null);
        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome Back " + username);
        response.setMessage("login success");
        response.setAccess_token(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }

    @Override
    public AuthResponse signup(SignupDTO req) throws Exception {
        keycloakService.createUser(req);

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setPhone(req.getPhone());
        createdUser.setRole(req.getRole());
        createdUser.setFullName(req.getFullName());
        createdUser.setUsername(req.getUsername());
        userRepository.save(createdUser);

        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(
                req.getUsername(),
                req.getPassword(),
                "password",
                null);

        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome " + createdUser.getEmail());
        response.setMessage("Register success");
        response.setAccess_token(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse = keycloakService.getAdminAccessToken(
                null,
                null,
                "refresh_token",
                refreshToken);
        AuthResponse response = new AuthResponse();

        response.setMessage("Access token received");
        response.setAccess_token(tokenResponse.getAccessToken());
        response.setRefresh_token(tokenResponse.getRefreshToken());
        return response;
    }

}
