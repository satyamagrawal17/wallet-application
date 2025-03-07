package com.example.walletApplication.controller;

import com.example.walletApplication.dto.CreateWalletRequestDto;
import com.example.walletApplication.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    @PostMapping
    public ResponseEntity<?> createWallet(@PathVariable Long userId, @RequestBody CreateWalletRequestDto request) {
        try {
            walletService.createWallet(userId, request.getCurrency());
            return new ResponseEntity<>("Wallet created successfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
