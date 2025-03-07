package com.example.walletApplication.service;

import com.example.walletApplication.exception.UserNotFoundException;
import com.example.walletApplication.model.User;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.walletApplication.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletService walletService;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public void register(UserDTO userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        walletService.createWallet(savedUser.getId(), userDto.getCurrency());
    }

    public void login(UserDTO userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent() && passwordEncoder.matches(userDto.getPassword(), existingUser.get().getPassword())) {
            return;
        }
        throw new IllegalArgumentException("User does not exist");
    }

    public User getUser(Long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User does not exist"));
    }
}
