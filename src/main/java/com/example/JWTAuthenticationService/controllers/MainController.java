package com.example.JWTAuthenticationService.controllers;

import com.example.JWTAuthenticationService.models.AuthenticationRequestDTO;
import com.example.JWTAuthenticationService.models.User;
import com.example.JWTAuthenticationService.security.JwtTokenProviderUtil;
import com.example.JWTAuthenticationService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProviderUtil jwtTokenProviderUtil;

    private final UserService userService;

    @Autowired
    public MainController(AuthenticationManager authenticationManager,
                          JwtTokenProviderUtil jwtTokenProviderUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProviderUtil = jwtTokenProviderUtil;
        this.userService = userService;
    }

    @PostMapping("token")
    public ResponseEntity getAuthentication(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            String username = authenticationRequestDTO.getUsername();
            String password = authenticationRequestDTO.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userService.findUserByUsername(username);

            if (username == null) {
                throw new UsernameNotFoundException("User with username " + username + " was not found");
            }

            String token = jwtTokenProviderUtil.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("bad credentials");
        }
    }
}
