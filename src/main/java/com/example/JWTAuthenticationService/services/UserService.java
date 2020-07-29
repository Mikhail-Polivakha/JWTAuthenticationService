package com.example.JWTAuthenticationService.services;

import com.example.JWTAuthenticationService.models.User;

import java.util.List;

public interface UserService {

    User registerNewUser(User user);

    List<User> getAllUsers();

    User findUserByUsername(String username);

    User findUserById(Long id);

    void deleteUserById(Long id);
}
