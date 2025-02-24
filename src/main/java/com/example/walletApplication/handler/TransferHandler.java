package com.example.walletApplication.handler;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.model.transaction.Transfer;
import com.example.walletApplication.repository.TransferRepository;
import com.example.walletApplication.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferHandler implements ITransactionHandler {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private WalletService walletService;

    @Override
    public void process(TransactionRequest request, Long originWalletId, Transaction savedTransaction) throws Exception {
        walletService.deposit(request.getRecipientWalletId(), request.getAmount());
        walletService.withdraw(originWalletId, request.getAmount());
        Transfer newTransfer = new Transfer();
        newTransfer.setTransaction(savedTransaction);
        Wallet savedRecipientWallet = walletService.getWallet(request.getRecipientWalletId());
        newTransfer.setToWallet(savedRecipientWallet);
        transferRepository.save(newTransfer);

    }

    @Override
    public ETransactionType getType() {
        return ETransactionType.TRANSFER;
    }
}
