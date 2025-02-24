package com.example.walletApplication.handler;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WithdrawHandler implements ITransactionHandler {

    @Autowired
    private WalletService walletService;


    @Override
    public void process(TransactionRequest request, Long originWalletId, Transaction savedTransaction) throws Exception {
        walletService.withdraw(originWalletId, request.getAmount());
    }

    @Override
    public ETransactionType getType() {
        return ETransactionType.WITHDRAW;
    }
}
