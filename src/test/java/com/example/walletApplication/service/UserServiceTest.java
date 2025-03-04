package com.example.walletApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.walletApplication.enums.ECurrency;
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
    @Mock
    private WalletService walletService;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegister_success() {
        UserDTO user = new UserDTO("testuser", "password123", ECurrency.USD);
        User savedUser = new User();
        savedUser.setUsername("testuser");
        savedUser.setPassword("encodedPassword123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty()); // No existing user
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123"); // Mock password encoding
        when(userRepository.save(savedUser)).thenReturn(savedUser); // Mock save method

        userService.register(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture()); // Verify save was called
        verify(walletService).createWallet(userCaptor.getValue(), ECurrency.USD); // Verify wallet creation

        User capturedUser = userCaptor.getValue();
        assertEquals("testuser", capturedUser.getUsername());
        assertEquals("encodedPassword123", capturedUser.getPassword()); // Check password hasheds
    }

    @Test
    void testRegister_existingUser() {
        UserDTO user = new UserDTO("testuser", "password123", ECurrency.USD);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User())); // Existing user

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(user);
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void testLogin_success() {
        UserDTO user = new UserDTO("testuser", "password123", ECurrency.USD);

        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("encodedPassword123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches("password123", "encodedPassword123")).thenReturn(true); // Mock password matching

        userService.login(user);
    }

    @Test
    void testLogin_failure() {
        UserDTO user = new UserDTO("testuser", "password123", ECurrency.USD);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty()); // No existing user

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login(user);
        });

        assertEquals("User does not exist", exception.getMessage());
    }
}