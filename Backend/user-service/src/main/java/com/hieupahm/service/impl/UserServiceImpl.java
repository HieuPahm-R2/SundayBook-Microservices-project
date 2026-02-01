package com.hieupahm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hieupahm.error.UserException;
import com.hieupahm.model.User;
import com.hieupahm.repository.UserRepository;
import com.hieupahm.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }

    @Override
    public User getUserFromJwtToken(String token) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserFromJwtToken'");
    }

    @Override
    public User getUserById(Long id) throws UserException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

}
