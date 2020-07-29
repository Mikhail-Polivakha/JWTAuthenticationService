package com.example.JWTAuthenticationService.services;

import com.example.JWTAuthenticationService.models.Role;
import com.example.JWTAuthenticationService.models.User;
import com.example.JWTAuthenticationService.repositories.RoleRepository;
import com.example.JWTAuthenticationService.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

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
        User user = new User("username","password");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role("USER_ROLE")));

        Assertions.assertDoesNotThrow(() -> userServiceImplementation.registerNewUser(user));
        Assertions.assertEquals(user, userServiceImplementation.registerNewUser(user));

        Mockito.verify(userRepository, Mockito.times(2)).save(user);
        Mockito.verify(roleRepository, Mockito.times(2)).findByName("ROLE_USER");
    }

    @Test
    void getAllUsers() {
        List<User> userListFetchedFromDataBase = new ArrayList<>();
        String expectedFirstName = "someFirstName";
        userListFetchedFromDataBase.add(new User(expectedFirstName, "password"));
        Mockito.when(userRepository.findAll()).thenReturn(userListFetchedFromDataBase);

        Assertions.assertEquals(expectedFirstName, userServiceImplementation.getAllUsers().get(0).getUsername());
        Assertions.assertEquals(1, userServiceImplementation.getAllUsers().size());

        Mockito.verify(userRepository, Mockito.times(2)).findAll();
    }

    @Test
    void findUserByUsername() {
        User userFetchedFromDatabase = new User("passedUsername", "somePassword");
        Mockito.when(userRepository.findByUsername("passedUsername")).thenReturn(Optional.of(userFetchedFromDatabase));

        Assertions.assertEquals("somePassword",
                userServiceImplementation.findUserByUsername("passedUsername").getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername("passedUsername");
    }

    @Test
    void findUserById() {
        User userFetchedFromDatabase = new User("passedUsername", "somePassword");
        userFetchedFromDatabase.setId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userFetchedFromDatabase));
        Assertions.assertEquals("somePassword", userServiceImplementation.findUserById(1L).getPassword());
        Assertions.assertEquals("passedUsername", userServiceImplementation.findUserById(1L).getUsername());

        Mockito.verify(userRepository, Mockito.times(2)).findById(1L);
    }

    @Test
    void deleteUserById() {
        User userFetchedFromDatabase = new User("passedUsername", "somePassword");
        userFetchedFromDatabase.setId(1L);

        Mockito.doNothing().when(userRepository).deleteById(1L);
        Assertions.assertDoesNotThrow(() -> userServiceImplementation.deleteUserById(1L));

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }
}
