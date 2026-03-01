package com.hieupahm.service;

import com.hieupahm.payload.request.SignupDTO;
import com.hieupahm.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;

    AuthResponse signup(SignupDTO req) throws Exception;

    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
