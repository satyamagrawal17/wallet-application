package com.example.walletApplication.handler;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ETransactionType;

public interface ITransactionHandler {
    public void process(TransactionRequest request, Long originWalletId) throws Exception;
    public ETransactionType getType();
}
