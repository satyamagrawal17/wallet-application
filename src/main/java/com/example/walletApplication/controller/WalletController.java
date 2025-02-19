package com.example.walletApplication.controller;

import com.example.walletApplication.entity.Money;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{user_id}/wallet")
public class WalletController {

    @GetMapping("/balance")
    public ResponseEntity<Money> getBalance() {
        return null;
    }

}
