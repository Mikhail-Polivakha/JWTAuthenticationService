package com.example.JWTAuthenticationService.repositories;

import com.example.JWTAuthenticationService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractUserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE ", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
