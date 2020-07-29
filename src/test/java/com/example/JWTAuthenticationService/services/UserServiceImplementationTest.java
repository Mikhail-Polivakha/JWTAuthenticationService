package com.example.JWTAuthenticationService.services;

import com.example.JWTAuthenticationService.models.User;
import com.example.JWTAuthenticationService.repositories.RoleRepository;
import com.example.JWTAuthenticationService.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplementationTest {

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    void generalInitialization() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void beforeEachInitialization() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        userServiceImplementation = new UserServiceImplementation(userRepository,
                roleRepository, passwordEncoder);
    }

    @Test
    void registerNewUser() {
        User user = new User(1, new Date(), new Date());
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void deleteUserById() {
    }
}