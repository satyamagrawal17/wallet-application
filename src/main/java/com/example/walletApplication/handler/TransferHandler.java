package com.example.walletApplication.handler;

import com.example.walletApplication.dto.MoneyDto;
import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.Money;
import com.example.walletApplication.model.Wallet;
import com.example.walletApplication.model.transaction.Transaction;
import com.example.walletApplication.model.transaction.Transfer;
import com.example.walletApplication.repository.TransactionRepository;
import com.example.walletApplication.repository.TransferRepository;
import com.example.walletApplication.repository.WalletRepository;
import com.example.walletApplication.service.CurrencyConversionGrpcService;
import com.example.walletApplication.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransferHandler implements ITransactionHandler {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    @Transactional
    @Override
    public void process(TransactionRequest request, Long originWalletId) throws Exception {
        Wallet originWallet = walletRepository.findById(originWalletId)
                .orElseThrow(() -> new IllegalArgumentException("Origin wallet not found"));
        Wallet recipientWallet = walletRepository.findById(request.getRecipientWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Recipient wallet not found"));


        request.setCurrency(originWallet.getBalance().getCurrency());
        walletService.withdraw(originWalletId, request.getAmount(), null);
        walletService.deposit(request.getRecipientWalletId(), request.getAmount(), originWallet.getBalance().getCurrency());
        Transaction newTransaction = new Transaction(request, originWallet);
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        Transfer newTransfer = new Transfer();
        newTransfer.setTransaction(savedTransaction);
        newTransfer.setToWallet(recipientWallet);
        transferRepository.save(newTransfer);
    }

    @Override
    public ETransactionType getType() {
        return ETransactionType.TRANSFER;
    }
}
