package com.example.walletApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.walletApplication.dto.UserDTO;
import com.example.walletApplication.model.User;
import com.example.walletApplication.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegister_success() {
        UserDTO user = new UserDTO();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty()); // No existing user
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123"); // Mock password encoding

        userService.register(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture()); // Verify save was called

        User savedUser = userCaptor.getValue();
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("encodedPassword123", savedUser.getPassword()); // Check password hashed
    }

    @Test
    void testRegister_existingUser() {
        UserDTO user = new UserDTO();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User())); // Existing user

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(user);
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void testLogin_success() {
        UserDTO user = new UserDTO();
        user.setUsername("testuser");
        user.setPassword("password123");

        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("encodedPassword123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encodedPassword123")).thenReturn(true); // Mock password matching

        userService.login(user);
    }

    @Test
    void testLogin_failure() {
        UserDTO user = new UserDTO();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty()); // No existing user

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login(user);
        });

        assertEquals("User does not exist", exception.getMessage());
    }
}
