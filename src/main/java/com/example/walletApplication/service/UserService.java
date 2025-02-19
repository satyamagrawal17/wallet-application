package com.example.walletApplication.service;

import com.example.walletApplication.ECurrency;
import com.example.walletApplication.entity.User;
import com.example.walletApplication.entity.Wallet;
import com.example.walletApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(User user) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u == user) {
                throw new IllegalArgumentException("User already exists");
            }
        }
        Wallet wallet = new Wallet(ECurrency.INR);
        user.setWallet(wallet);
        userRepository.save(user);
    }

    public void login(User user) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u == user) {
                return;
            }
        }
        throw new IllegalArgumentException("User does not exist");

    }
}
