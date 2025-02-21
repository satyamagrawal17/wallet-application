package com.example.walletApplication.service;

import com.example.walletApplication.exception.UserNotFoundException;
import com.example.walletApplication.exception.WalletNotFoundException;
import com.example.walletApplication.model.User;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.repository.WalletRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;


//    public void createWallet() {
//        System.out.println("Wallet created");
//    }
public Wallet getWallet(Long walletId) throws Exception {
    return walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet does not exist"));
}

    public void deposit(Long originWalletId, @NotNull @NotBlank(message = "Amount is mandatory") double amount) throws Exception {
        Wallet wallet = getWallet(originWalletId);
        wallet.addBalance(amount);
        walletRepository.save(wallet);
    }

    public void withdraw(Long originWalletId, @NotNull @NotBlank(message = "Amount is mandatory") double amount) throws Exception {
        Wallet wallet = getWallet(originWalletId);
        wallet.subtractBalance(amount);
        walletRepository.save(wallet);
    }
}
