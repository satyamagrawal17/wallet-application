package com.example.walletApplication.handler;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.transaction.Transaction;

public interface ITransactionHandler {
    public void process(TransactionRequest request, Long originWalletId, Transaction savedTransaction) throws Exception;
    public ETransactionType getType();
}
