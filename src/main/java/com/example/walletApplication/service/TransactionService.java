package com.example.walletApplication.service;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.dto.TransactionResponse;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.handler.ITransactionHandler;
import com.example.walletApplication.model.User;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.model.transaction.Transfer;
import com.example.walletApplication.registry.TransactionHandlerRegistry;
import com.example.walletApplication.repository.TransactionRepository;
import com.example.walletApplication.repository.TransferRepository;
import com.example.walletApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionHandlerRegistry transactionHandlerRegistry;

    @Transactional
    public void createTransaction(TransactionRequest transactionRequest, Long userId, Long originWalletId) throws Exception {
        if(!walletService.doesUserBelongTo(originWalletId, userId)) {
            throw new IllegalArgumentException("Wallet does not belong to user");
        }
        Wallet savedOriginWallet = walletService.getWallet(originWalletId);

        Transaction newTransaction = new Transaction(transactionRequest, savedOriginWallet);
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        ITransactionHandler handler = transactionHandlerRegistry.getHandler(transactionRequest.getTransactionType());
        if(Objects.isNull(handler)) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
        handler.process(transactionRequest, originWalletId, savedTransaction);

    }
}
