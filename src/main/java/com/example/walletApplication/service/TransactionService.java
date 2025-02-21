package com.example.walletApplication.service;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.dto.TransactionResponse;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public void createTransaction(TransactionRequest transactionRequest, Long userId, Long originWalletId) {
        transactionRepository.save(new Transaction());

    }
}
