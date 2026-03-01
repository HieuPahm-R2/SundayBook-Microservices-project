package com.hieupahm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieupahm.payload.request.LoginDTO;
import com.hieupahm.payload.request.SignupDTO;
import com.hieupahm.payload.response.ApiResponse;
import com.hieupahm.payload.response.ApiResponseBody;
import com.hieupahm.payload.response.AuthResponse;
import com.hieupahm.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse> HomeControllerHandler() {
        return ResponseEntity.status(
                HttpStatus.OK)
                .body(new ApiResponse(
                        "welcome to zosh property booking system, user api"));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseBody<AuthResponse>> signupHandler(
            @RequestBody SignupDTO req) throws Exception {

        System.out.println("signup dto " + req);
        AuthResponse response = authService.signup(req);

        return ResponseEntity.ok(new ApiResponseBody<>(
                true,
                "User created successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseBody<AuthResponse>> loginHandler(
            @RequestBody LoginDTO req) throws Exception {
        AuthResponse response = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(new ApiResponseBody<>(
                true,
                "User logged in successfully",
                response));
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<ApiResponseBody<AuthResponse>> getAccessTokenHandler(@PathVariable String refreshToken)
            throws Exception {

        AuthResponse response = authService.getAccessTokenFromRefreshToken(refreshToken);

        return ResponseEntity.ok(new ApiResponseBody<>(
                true,
                "refresh token received successfully",
                response));
    }
}
