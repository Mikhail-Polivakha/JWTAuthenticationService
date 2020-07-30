package com.example.JWTAuthenticationService.services;

import com.example.JWTAuthenticationService.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImplemenation implements UserDetailsService {

    private UserService userServiceImplementation;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User fetchedUser = userServiceImplementation.findUserByUsername(username);
        if (fetchedUser == null) {
            log.warn("user with username : {} was not found", username);
            throw new UsernameNotFoundException("user with username " + username + "was not found");
        } else {
            log.info("user with username : {} was successfully fetched from database", username);
            return ConversionUserIntoUserDetailsUtil.convertPassedUserIntoCustomUserDetails(fetchedUser);
        }
    }
}
