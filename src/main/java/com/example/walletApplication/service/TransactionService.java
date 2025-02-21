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


    public void createTransaction(TransactionRequest transactionRequest, Long userId, Long originWalletId) throws Exception {
        Wallet savedOriginWallet = walletService.getWallet(originWalletId);
        if (savedOriginWallet == null) {
            throw new IllegalArgumentException("Invalid origin wallet ID");
        }

        Wallet savedRecipientWallet = null;
        if (transactionRequest.getRecipientWalletId() != null) {
            savedRecipientWallet = walletService.getWallet(transactionRequest.getRecipientWalletId());
            if (savedRecipientWallet == null) {
                throw new IllegalArgumentException("Invalid recipient wallet ID");
            }
        }
        if(!Objects.equals(savedOriginWallet.getUser().getId(), userId)) {
            throw new IllegalArgumentException("Wallet does not belong to user");
        }
        if(transactionRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount should be greater than 0");
        }
        ITransactionHandler handler = transactionHandlerRegistry.getHandler(transactionRequest.getTransactionType());
        handler.process(transactionRequest, originWalletId);
        Transaction newTransaction = new Transaction();
        newTransaction.setOriginWallet(savedOriginWallet);
        newTransaction.setTransactionType(transactionRequest.getTransactionType());
        newTransaction.setAmount(transactionRequest.getAmount());
        transactionRepository.save(newTransaction);
        if(transactionRequest.getTransactionType() == ETransactionType.TRANSFER) {
            Transfer newTransfer = new Transfer();
            newTransfer.setTransaction(newTransaction);
            savedRecipientWallet = walletService.getWallet(transactionRequest.getRecipientWalletId());
            newTransfer.setToWallet(savedRecipientWallet);
            transferRepository.save(newTransfer);
        }

    }
}
