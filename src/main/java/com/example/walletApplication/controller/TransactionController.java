//package com.example.walletApplication.controller;
//
//import com.example.walletApplication.dto.TransactionRequest;
//import com.example.walletApplication.service.TransactionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users/{user_id}/wallets/{wallet_id}/transactions")
//public class TransactionController {
//
//    @Autowired
//    private TransactionService transactionService;
//
//    @PostMapping
//    public ResponseEntity<?> createTransaction(@PathVariable Long user_id, @PathVariable Long wallet_id, @RequestBody TransactionRequest transactionRequest) {
//        try {
//            transactionRequest.setOriginWalletId(wallet_id);
//            transactionService.createTransaction(transactionRequest);
//            return ResponseEntity.ok("Transaction created successfully");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
