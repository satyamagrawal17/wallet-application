package com.example.walletApplication.controller;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.dto.TransactionResponse;
import com.example.walletApplication.handler.ITransactionHandler;
import com.example.walletApplication.registry.TransactionHandlerRegistry;
import com.example.walletApplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallets/{walletId}/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping
    public ResponseEntity<?> createTransaction(@PathVariable Long userId, @PathVariable(name = "walletId") Long originWalletId, @RequestBody TransactionRequest transactionRequest) {
        try {
            transactionService.createTransaction(transactionRequest, userId, originWalletId);
            return new ResponseEntity<>("Transaction created successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
