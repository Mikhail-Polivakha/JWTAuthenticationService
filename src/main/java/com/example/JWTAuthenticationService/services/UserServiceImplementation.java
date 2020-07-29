package com.example.JWTAuthenticationService.services;

import com.example.JWTAuthenticationService.models.ActiveStatus;
import com.example.JWTAuthenticationService.models.Role;
import com.example.JWTAuthenticationService.models.User;
import com.example.JWTAuthenticationService.repositories.RoleRepository;
import com.example.JWTAuthenticationService.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = {
            RuntimeException.class,
            NonUniqueResultException.class
    }, propagation = Propagation.REQUIRES_NEW)
    public User registerNewUser(User userToRegister) {
        Role basicRole = new Role();
        try {
            basicRole = roleRepository.findByName("ROLE_USER").orElseThrow();
            log.info("Role {} was successfully fetched from database", "USER_ROLE");
        } catch (NoSuchElementException e) {
            log.warn("Role {} was not fetched from database", "USER_ROLE");
        }
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(basicRole);

        userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        userToRegister.setRoles(userRoles);
        userToRegister.setActiveStatus(ActiveStatus.ACTIVE);

        userRepository.save(userToRegister);
        log.info("New user with id {} was successfully sent", userToRegister.getId());
        return userToRegister;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allFetchedUsers = userRepository.findAll();
        log.info("All users was successfully fetched from database");
        return allFetchedUsers;
    }

    @Override
    public User findUserByUsername(String username) {
        User fetchedUser = new User();
        try {
            fetchedUser = userRepository.findByUsername(username).orElseThrow();
            log.info("User with username : {} was successfully fetched from database", username);
        } catch (NoSuchElementException e) {
            log.warn("User with username : {} was not found in the database", username);
        }
        return fetchedUser;
    }

    @Override
    public User findUserById(Long id) {
        User fetchedUser = new User();
        try {
            fetchedUser = userRepository.findById(id).orElseThrow();
            log.info("User with id : {} was successfully fetched from database", id);
        } catch (NoSuchElementException e) {
            log.warn("User with id : {} was not found in the database", id);
        }
        return fetchedUser;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        log.info("User with Id : {} was successfully deleted", id);
    }
}
