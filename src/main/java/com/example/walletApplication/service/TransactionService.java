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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {


    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionHandlerRegistry transactionHandlerRegistry;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void createTransaction(TransactionRequest transactionRequest, Long userId, Long originWalletId) throws Exception {
        if(!walletService.doesUserBelongTo(originWalletId, userId)) {
            throw new IllegalArgumentException("Wallet does not belong to user");
        }
        Wallet savedOriginWallet = walletService.getWallet(originWalletId);


        ITransactionHandler handler = transactionHandlerRegistry.getHandler(transactionRequest.getTransactionType());
        if(Objects.isNull(handler)) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
        handler.process(transactionRequest, originWalletId);

    }

//    public List<Transaction> fetchAllTransactions() {
//        List<Transaction> transactionList = new ArrayList<>();
//        List<Transaction> savedTransactions = transactionRepository.findAllById()
//        return transactionList;
//    }
}
