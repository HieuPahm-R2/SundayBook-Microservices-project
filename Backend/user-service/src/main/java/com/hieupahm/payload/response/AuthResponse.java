package com.hieupahm.payload.response;

import com.hieupahm.domain.UserRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String access_token;
    private String refresh_token;
    private String message;
    private String title;
    private UserRoleEnum role;
}
