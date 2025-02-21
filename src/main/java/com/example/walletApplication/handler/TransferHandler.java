package com.example.walletApplication.handler;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ETransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransferHandler implements ITransactionHandler {
    @Override
    public void process(TransactionRequest request, Long originWalletId) {
        // TODO Auto-generated method stub
    }

    @Override
    public ETransactionType getType() {
        return ETransactionType.TRANSFER;
    }
}
