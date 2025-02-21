package com.example.walletApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired

    public void createWallet() {
        System.out.println("Wallet created");
    }
}
