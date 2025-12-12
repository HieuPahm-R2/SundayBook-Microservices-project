package com.hieupahm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hieupahm.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
