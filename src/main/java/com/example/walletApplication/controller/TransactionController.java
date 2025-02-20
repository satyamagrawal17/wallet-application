package com.example.walletApplication.controller;

import com.example.walletApplication.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{user_id}/wallets/{wallet_id}/transactions")
public class TransactionController {
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        return null;
    }
}
