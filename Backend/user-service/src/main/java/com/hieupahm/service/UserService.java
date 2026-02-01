package com.hieupahm.service;

import java.util.List;

import com.hieupahm.error.UserException;
import com.hieupahm.model.User;

public interface UserService {
    User getUserByEmail(String email) throws Exception;

    User getUserFromJwtToken(String token) throws Exception;

    User getUserById(Long id) throws UserException;

    List<User> getAllUsers();
}
