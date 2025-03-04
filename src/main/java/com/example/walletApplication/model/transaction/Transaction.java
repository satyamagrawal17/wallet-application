package com.example.walletApplication.model.transaction;

import com.example.walletApplication.dto.TransactionRequest;
import com.example.walletApplication.enums.ECurrency;
import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.model.Wallet;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_wallet_id", nullable = false)
    private Wallet originWallet;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private ETransactionType transactionType;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ECurrency currency;


    public Transaction(TransactionRequest transactionRequest, Wallet originWallet) {
        if(transactionRequest.getAmount() < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.originWallet = originWallet;
        this.transactionType = transactionRequest.getTransactionType();
        this.amount = transactionRequest.getAmount();
        this.currency = transactionRequest.getCurrency();

    }
}
