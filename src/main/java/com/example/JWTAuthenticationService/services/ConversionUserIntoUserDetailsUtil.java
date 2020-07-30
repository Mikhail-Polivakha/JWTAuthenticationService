package com.example.JWTAuthenticationService.services;

import com.example.JWTAuthenticationService.models.CustomUserDetails;
import com.example.JWTAuthenticationService.models.Role;
import com.example.JWTAuthenticationService.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;


public class ConversionUserIntoUserDetailsUtil {

    private ConversionUserIntoUserDetailsUtil() {
    }

    public static CustomUserDetails convertPassedUserIntoCustomUserDetails(User userToConvert) {
        return new CustomUserDetails(
                userToConvert.getId(),
                userToConvert.getUsername(),
                userToConvert.getPassword(),
                null,
                userToConvert.getEmail(),
                userToConvert.getFirstName(),
                userToConvert.getLastName(),
                userToConvert.isEnabled()
        );
    }

    private static List<GrantedAuthority> convertRolesIntoGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
