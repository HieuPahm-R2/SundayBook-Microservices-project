package com.hieupahm.payload.request;

import com.hieupahm.domain.UserRoleEnum;

import lombok.Data;

@Data
public class SignupDTO {
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private String username;
    private UserRoleEnum role;
}
