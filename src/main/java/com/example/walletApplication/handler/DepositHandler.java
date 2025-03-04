package com.example.walletApplication.handler;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.repository.TransactionRepository;
import com.example.walletApplication.repository.WalletRepository;
import com.example.walletApplication.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepositHandler implements ITransactionHandler{

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public void process(TransactionRequest request, Long originWalletId) throws Exception {
        Wallet originWallet = walletRepository.findById(originWalletId)
                .orElseThrow(() -> new IllegalArgumentException("Origin wallet not found"));
        if(request.getCurrency() == null) {
            request.setCurrency(originWallet.getBalance().getCurrency());
        }
        walletService.deposit(originWalletId, request.getAmount(), request.getCurrency());
        Transaction newTransaction = new Transaction(request, originWallet);
        transactionRepository.save(newTransaction);

    }

    @Override
    public ETransactionType getType() {
        return ETransactionType.DEPOSIT;
    }
}
