package com.example.JWTAuthenticationService.security;

import com.example.JWTAuthenticationService.models.Role;
import com.example.JWTAuthenticationService.models.User;
import com.example.JWTAuthenticationService.services.UserDetailsServiceImplemenation;
import com.example.JWTAuthenticationService.services.UserServiceImplementation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtTokenProviderUtilTest {

    private JwtTokenProviderUtil jwtTokenProviderUtil;

    @MockBean
    UserServiceImplementation userServiceImplementation;

    @BeforeAll
    void mockitoInit() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void initialization() {
        jwtTokenProviderUtil = new JwtTokenProviderUtil();
    }

    @Test
    void createToken() {
        String username = "username";
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));

        jwtTokenProviderUtil.setSecret("secret");
        jwtTokenProviderUtil.init();
        Assertions.assertDoesNotThrow(() -> jwtTokenProviderUtil.createToken(username, roles));
        Assert.notNull(jwtTokenProviderUtil.createToken(username, roles), "Should return a token");
        Assert.isInstanceOf(String.class, jwtTokenProviderUtil.createToken(username, roles));
    }

    @Test
    @Disabled
    void getAuthentication() {
        String username = "username";
        userServiceImplementation = Mockito.mock(UserServiceImplementation.class);
        Mockito.when(userServiceImplementation.findUserByUsername(username)).thenReturn(new User());
        Assertions.assertDoesNotThrow(() -> jwtTokenProviderUtil.getAuthentication(""));
    }

    @Test
    void getUsername() {
    }

    @Test
    void resolveToken() {
    }

    @Test
    void validateToken() {
    }
}